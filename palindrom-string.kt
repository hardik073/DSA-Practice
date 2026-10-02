// 1. Goal: Check if string reads same forward and backward, ignoring non-alphanumeric & case
// 2. Approach: Two pointers (start at 0, end at s.length - 1)
// 3. Loop condition: start < end
// 4. Skip non-alphanumeric characters on the left (start++)
// 5. Skip non-alphanumeric characters on the right (end--)
// 6. Compare characters ignoring case using lowercaseChar()
// 7. If mismatch found -> return false
// 8. If matched -> move both pointers inward (start++, end--)
// 9. If pointers cross without mismatch -> return true
// 10. Space complexity is O(1) as no extra string is created

fun isPalindrome(s: String): Boolean {
    var start = 0
    var end = s.length - 1

    while (start < end) {
        // Skip non-alphanumeric characters from left
        if (!s[start].isLetterOrDigit()) {
            start++
            continue
        }

        // Skip non-alphanumeric characters from right
        if (!s[end].isLetterOrDigit()) {
            end--
            continue
        }

        // Compare characters in lowercase
        if (s[start].lowercaseChar() != s[end].lowercaseChar()) {
            return false
        }

        start++
        end--
    }

    return true
}

fun main() {
    println(isPalindrome("A man, a plan, a canal: Panama")) // true
    println(isPalindrome("race a car"))                     // false
    println(isPalindrome(" "))                              // true
}
