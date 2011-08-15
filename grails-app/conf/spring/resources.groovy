import grails.util.Environment

// Place your Spring DSL code here
beans =  {
   // ----- Feed Fetcher ------ //
   itemService(feedr.ItemService)

   httpSyndFeedReceiver(feedr.fetcher.HttpSyndFeedReceiver) { syndFeedReceiver = itemService }

   httpFeedFetcher(feedr.fetcher.HttpFeedFetcher) { httpFeedReceiver = httpSyndFeedReceiver }

   // ----- Item Fetcher ------ //
   itemImageFetcher(feedr.fetcher.ItemImageFetcher)
   itemPageFetcher(feedr.fetcher.ItemPageFetcher)
   itemFetcher(feedr.fetcher.ItemFetcher) {
      imageFetcher = itemImageFetcher
      pageFetcher = itemPageFetcher
   }

   switch(Environment.current) {
      case Environment.PRODUCTION:
      case Environment.DEVELOPMENT:
         // ----- Feed Bot ----- //
         feedBot(feedr.fetcher.FeedBot) { feedFetcher = httpFeedFetcher }

         // ----- Feed Bot Job ----- //
         feedBotJobDetail(org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean) {
            targetObject = feedBot
            targetMethod = 'run'
            concurrent = false
         }

         feedBotJobTrigger(org.springframework.scheduling.quartz.SimpleTriggerBean) {
            jobDetail = feedBotJobDetail
            startDelay = 10000
            repeatInterval = 20000
         }

         // ----- Item Bot ----- //
         itemBot(feedr.fetcher.ItemBot) { itemFetcher = itemFetcher }

         // ----- Item Bot Job ----- //
         itemBotJobDetail(org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean) {
            targetObject = itemBot
            targetMethod = 'run'
            concurrent = false
         }

         itemBotJobTrigger(org.springframework.scheduling.quartz.SimpleTriggerBean) {
            jobDetail = itemBotJobDetail
            startDelay = 20000
            repeatInterval = 30000
         }

         schedulerFactoryBean(org.springframework.scheduling.quartz.SchedulerFactoryBean) {
            triggers = [
               feedBotJobTrigger,
               itemBotJobTrigger
            ]
         }
         break
   }
}
