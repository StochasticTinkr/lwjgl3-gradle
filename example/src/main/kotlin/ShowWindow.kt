import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.*

fun main() {
    Configuration.GLFW_LIBRARY_NAME.set("glfw_async")
    glfwInit()
    glfwDefaultWindowHints()
    glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE)
    val window = glfwCreateWindow(800, 600, "Hello World", 0L, 0L)
    if (window == 0L) {
        throw RuntimeException("Failed to create the GLFW window")
    }
    glfwSetKeyCallback(window) { window, key, scancode, action, mods ->
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            glfwSetWindowShouldClose(window, true)
        }
    }
    while (!glfwWindowShouldClose(window)) {
        glfwWaitEvents()
    }
}