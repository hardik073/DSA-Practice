class LruCache<K,V>(
	private val capacity: Int
){
    
    private class Node<K,V>(
    	val key: K,
        var value: V
    ){
        var prev: Node<K, V>? = null
        var next: Node<K, V>? = null
    }
    
    private val cache = HashMap<K, Node<K,V>>()
    
    init {
        require(capacity>0){
            "Capacity must be greater than zero"
        }
    }
    
    
    fun get(key: K): V?{
        val node = cache[key] ?: return null
        moveToFront(node)
        return node.value
    }
    
    fun put(key:k, value: V){
        val existingNode = cache[key]
        
        if(existingNode != null){
            existingNode.value = value
            moveToFront(existingNode)
            return
        }
        
        val newNode = Node(key, value)
        
        cache[key] = newNode
        
        addToFront(newNode)
        
        if(cache.size > capacity){
            val removedNode = removeLast()
           	if(removedNode != null){
                cache.remove(removedNode.key)
            }
        }
    }
    
    fun size() : Int = cache.size
    
    private fun addToFront(node: Node<K,V>){
        node.prev = null
        node.next = head
        
        head?.prev = node
        
        head = node
        
        if(tail == null){
            tail = node
        }
    }
    
    private fun removeNode(node: Node<K, V>) {
        val previous = node.prev
        val next = node.next

        if (previous != null) {
            previous.next = next
        } else {
            head = next
        }

        if (next != null) {
            next.prev = previous
        } else {
            tail = previous
        }

        node.prev = null
        node.next = null
    }

    private fun moveToFront(node: Node<K, V>) {
        if (node === head) return

        removeNode(node)
        addToFront(node)
    }

    private fun removeLast(): Node<K, V>? {
        val node = tail ?: return null

        removeNode(node)

        return node
    }
    
    
}

class LruCacheTest {

    @Test
    fun `get returns inserted value`() {
        val cache = LruCache<String, Int>(2)

        cache.put("A", 1)

        assertEquals(1, cache.get("A"))
    }

    @Test
    fun `get returns null for missing key`() {
        val cache = LruCache<String, Int>(2)

        assertNull(cache.get("A"))
    }

    @Test
    fun `least recently used item is evicted`() {
        val cache = LruCache<String, Int>(2)

        cache.put("A", 1)
        cache.put("B", 2)
        cache.put("C", 3)

        assertNull(cache.get("A"))
        assertEquals(2, cache.get("B"))
        assertEquals(3, cache.get("C"))
    }

    @Test
    fun `get updates recency`() {
        val cache = LruCache<String, Int>(2)

        cache.put("A", 1)
        cache.put("B", 2)

        cache.get("A")

        cache.put("C", 3)

        assertEquals(1, cache.get("A"))
        assertNull(cache.get("B"))
        assertEquals(3, cache.get("C"))
    }

    @Test
    fun `put existing key updates value`() {
        val cache = LruCache<String, Int>(2)

        cache.put("A", 1)
        cache.put("A", 2)

        assertEquals(2, cache.get("A"))
    }

    @Test
    fun `put existing key updates recency`() {
        val cache = LruCache<String, Int>(2)

        cache.put("A", 1)
        cache.put("B", 2)

        cache.put("A", 10)
        cache.put("C", 3)

        assertEquals(10, cache.get("A"))
        assertNull(cache.get("B"))
    }

    @Test
    fun `capacity one keeps only latest item`() {
        val cache = LruCache<String, Int>(1)

        cache.put("A", 1)
        cache.put("B", 2)

        assertNull(cache.get("A"))
        assertEquals(2, cache.get("B"))
    }

    @Test
    fun `zero capacity throws exception`() {
        assertThrows<IllegalArgumentException> {
            LruCache<String, Int>(0)
        }
    }
}
