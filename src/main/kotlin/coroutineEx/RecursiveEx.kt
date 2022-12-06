package coroutineEx

import Section
import runAll

class RecursiveEx {
    private class Tree(val left: Tree?, val right: Tree?)

    private fun badRecursiveFunction() {
        fun depth(t: Tree?): Int =
            if (t == null) 0 else maxOf(
                depth(t.left), // recursive call one
                depth(t.right) // recursive call two
            ) + 1

        val n = 100_000
        val deepTree = generateSequence(Tree(null, null)) { prev ->
            Tree(prev, null)
        }.take(n).last()
        // println(depth(deepTree)) //this will produce StackOverflowError
    }

    private fun overlyComplicatedRecursiveFunction() {
        fun depth(t: Tree?): Int {
            if (t == null) return 0
            class Frame(val node: Tree, var state: Int = 0, var depth: Int = 1)

            val stack = ArrayList<Frame>()
            val root = Frame(t)
            stack.add(root)
            while (stack.isNotEmpty()) {
                val frame = stack.last()
                when (frame.state++) {
                    0 -> frame.node.left?.let { l -> stack.add(Frame(l)) }
                    1 -> frame.node.right?.let { r -> stack.add(Frame(r)) }
                    2 -> {
                        stack.removeLast()
                        stack.lastOrNull()?.let { p -> p.depth = maxOf(p.depth, frame.depth + 1) }
                    }
                }
            }
            return root.depth
        }

        val n = 100_000
        val deepTree = generateSequence(Tree(null, null)) { prev ->
            Tree(prev, null)
        }.take(n).last()
        println(depth(deepTree))
    }

    private fun goodRecursiveFunction() {
    }

    init {
        runAll(
            "Recursive examples",
            Section("Failing recursive function", ::badRecursiveFunction),
            Section("Overly complicated recursive function", ::overlyComplicatedRecursiveFunction)
        )
    }
}