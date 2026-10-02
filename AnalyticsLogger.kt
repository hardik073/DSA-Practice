
fun main() {

    
    val logger = AnalyticsLogger()
    
    logger.logEvent(
            eventName = "login_success",
            params = mapOf(
                "method" to "email"
            )
        )
    
    
    println(logger.gerRecentEvents().size)
}

class AnalyticsLogger{
    
    companion object {
        private const val MAX_EVENTS = 10
    }
 
    private val events = ArrayDeque<AnalyticsEvent>()
    
    private var callback : ((AnalyticsEvent) -> Unit)? = null
    
    private val lock = Any()
    
    fun logEvent(eventName: String, params: Map<String, Any>) {
		val event = AnalyticsEvent(
        	eventName = eventName,
            params = params
        )
        
        synchronized(lock){
            if(events.size >= MAX_EVENTS){
    	        events.removeFirst()
	        }
      		events.addLast(event)
        }
        
        callback?.invoke(event)
    }
    
    
    fun gerRecentEvents() : List<AnalyticsEvent>{
        synchronized(lock){
 	       return events.toList()            
        }
    }
    
    fun setEventCallback(
    	callback :((AnalyticsEvent) -> Unit)?
    ){
        this.callback = callback
    }
}

data class AnalyticsEvent(
	val eventName : String,
    val params:  Map<String, Any>
)


@Test
fun `keep only the least 10 events`() {
    
    val logger = AnalyticsLogger()
    
    repeat(12){ index ->
        logget.logEvent(
        	eventName = "event_$index",
            paramts = emptyMap()
        )
    }
    
    val events = logger.getRecentEvents()
    
    assertEquals(10, events.size())
    assertEquals("event_2", events.first().eventName)
}











