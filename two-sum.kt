// 1. Goal: find two numbers that add up to the target; return their indexes
// 2. Use a HashMap to remember numbers seen so far and their positions
// 3. For each number, calculate complement = target - current number
// 4. Check if complement already exists in the HashMap
// 5. If yes - found the pair, return [complement's index, current index]
// 6. If no - store the current number and its index in the HashMap
// 7. Continue for every element in the array
// 8. This runs in O(n) - one pass, each HashMap operation is O(1)
// 9. HashMap stores: number as key, index as value
// 10. Problem guarantees one solution, so we always find the answer


fun main() {    
  val nums = intArrayOf(7, 17, 20, 2)
    val target = 9
    
    val result = findIndices(target, nums)
    println("Indices: [${result[0]}, ${result[1]}]")
}

fun findIndices(target : Int, array : IntArray) : IntArray {
    val hashMap = HashMap<Int, Int>()

    for(i in array.indices){
        val complement = target - array[i]
        if(hashMap.containsKey(complement)){
            return intArrayOf(hashMap[complement]!!, i)
        }

        hashMap[array[i]] = i
    }
    return intArrayOf()
}
