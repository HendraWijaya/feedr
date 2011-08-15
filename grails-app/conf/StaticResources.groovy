modules = {
    
    blueprint {
        resource url:[dir:'css/blueprint',file:'screen.css'], attrs:[media:'screen, projection']
        resource url:[dir:'css/blueprint',file:'print.css'], attrs:[media:'print']
        resource url:[dir:'css/blueprint',file:'ie.css'], attrs:[media:'screen, projection'],
                 wrapper: { s -> "<!--[if lt IE 8]>$s<![endif]-->" }
    }
    
    dialog {
        dependsOn 'jquery'
        defaultBundle 'ui'
        
        resource '/css/smoothness/jquery-ui-1.8.13.custom.css'
        resource '/js/jquery-ui-1.8.13.custom.min.js'
        
        resource '/css/dialog.css'
    }
    
    core {
        dependsOn 'blueprint'
        defaultBundle 'ui'
        
        resource url:'/css/main.css'
    }

    'list' {
        dependsOn 'core'
        defaultBundle 'ui'
        
        resource url:'/css/list.css'
    }
    
    form { 
        dependsOn 'core' 
        defaultBundle 'ui'
        
        resource url:'/css/form.css' 
        resource url:'/css/button.css'
    } 
    
    item {
        dependsOn 'core'
        defaultBundle 'ui'
        
        resource url:'/css/item.css'
    }
}