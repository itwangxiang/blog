# Kotlin

> 官方文档 - [en](https://kotlinlang.org/docs/reference/) / [zh-cn](https://www.kotlincn.net/docs/reference/)
>> 惯用语法 - [en](https://kotlinlang.org/docs/reference/idioms.html) / [zh-cn](https://www.kotlincn.net/docs/reference/idioms.html)

## 目录

- [基础](#基础)
  - [基础语法](#基础语法)
  - [惯用语法](#惯用语法)

## 基础

### 基础语法

- 包
  
  ```kotlin
  package my.demo
  import kotlin.text.*
  ```

- 函数
  
  ```kotlin
  fun sum(a: Int, b: Int): Int {
    return a + b
  }

  //表达式作为函数体，返回类型自动推断
  fun sum(a: Int, b: Int) = a + b

  //无返回值的函数
  fun printSum(a: Int, b: Int): Unit {
    println("sum of $a and $b is ${a + b}")
  }

  //Unit 返回类型可以省略
  fun printSum(a: Int, b: Int) {
    println("sum of $a and $b is ${a + b}")
  }
  ```

- 变量
  
  ```kotlin
  //定义只读局部变量使用 val 。只能赋值一次
  val a: Int = 1  // 立即赋值
  val b = 2   // 自动推断出 `Int` 类型
  val c: Int  // 如果没有初始值类型不能省略
  c = 3       // 明确赋值

  //可重复赋值的变量使用 var 关键字
  var x = 5 // 自动推断出 `Int` 类型
  x += 1
  ```

- 注解
  
  ```kotlin
  // 这是一个行注释

  /* 这是一个多行的
    块注释。 */
  ```

- 字符串模板
  
  ```kotlin
  var a = 1
  // 模板中的简单名称：
  val s1 = "a is $a"

  a = 2
  // 模板中的任意表达式：
  val s2 = "${s1.replace("is", "was")}, but now is $a"
  ```

- 条件表达式
  
  ```kotlin
  fun maxOf(a: Int, b: Int): Int {
      if (a > b) {
          return a
      } else {
          return b
      }
  }

  fun maxOf(a: Int, b: Int) = if (a > b) a else b
  ```

- 空值与 `null` 检测

  ```kotlin
  //当某个变量的值可以为 null 的时候，必须在声明处的类型后添加 ? 来标识该引用可为空
  
  //如果 str 的内容不是数字返回 null：
  fun parseInt(str: String): Int? {
      // ……
  }

  //使用返回可空值的函数
  fun printProduct(arg1: String, arg2: String) {
      val x = parseInt(arg1)
      val y = parseInt(arg2)

      // 直接使用 `x * y` 会导致编译错误，因为它们可能为 null
      if (x != null && y != null) {
          // 在空检测后，x 与 y 会自动转换为非空值（non-nullable）
          println(x * y)
      }
      else {
          println("'$arg1' or '$arg2' is not a number")
      }
  }  
  ```

- `is` 类型检查与自动类型转换

  ```kotlin
  //is 运算符检测一个表达式是否某类型的一个实例。
  //如果一个不可变的局部变量或属性已经判断出为某类型，那么检测后的分支中可以直接当作该类型使用，无需显式转换

  fun getStringLength(obj: Any): Int? {
      if (obj is String) {
          // `obj` 在该条件分支内自动转换成 `String`
          return obj.length
      }

      // 在离开类型检测分支后，`obj` 仍然是 `Any` 类型
      return null
  }  
  ```

- `for` 循环

  ```kotlin
  val items = listOf("apple", "banana", "kiwifruit")
  for (item in items) {
      println(item)
  }

  val items = listOf("apple", "banana", "kiwifruit")
  for (index in items.indices) {
      println("item at $index is ${items[index]}")
  }  
  ```

- `while` 循环

  ```kotlin
  val items = listOf("apple", "banana", "kiwifruit")
  var index = 0
  while (index < items.size) {
      println("item at $index is ${items[index]}")
      index++
  }  
  ```

- `when` 表达式

  ```kotlin
  fun describe(obj: Any): String =
      when (obj) {
          1          -> "One"
          "Hello"    -> "Greeting"
          is Long    -> "Long"
          !is String -> "Not a string"
          else       -> "Unknown"
      }  
  ```

- `range` 范围

  ```kotlin
  //使用 in 运算符来检测某个数字是否在指定区间内
  val x = 10
  val y = 9
  if (x in 1..y+1) {
      println("fits in range")
  }

  //检测某个数字是否在指定区间外
  val list = listOf("a", "b", "c")

  if (-1 !in 0..list.lastIndex) {
      println("-1 is out of range")
  }
  if (list.size !in list.indices) {
      println("list size is out of valid list indices range, too")
  }

  //区间迭代:
  for (x in 1..5) {
      print(x)
  }

  //数列迭代
  for (x in 1..10 step 2) {
      print(x)
  }
  println()
  for (x in 9 downTo 0 step 3) {
      print(x)
  }
  ```

- `Collections`
  
  ```kotlin
  //对集合进行迭代
  for (item in items) {
      println(item)
  }

  //使用 in 运算符来判断集合内是否包含某实例
  when {
      "orange" in items -> println("juicy")
      "apple" in items -> println("apple is fine too")
  }

  //使用 lambda 表达式来过滤（filter）与映射（map）集合
  val fruits = listOf("banana", "avocado", "apple", "kiwifruit")
  fruits
    .filter { it.startsWith("a") }
    .sortedBy { it }
    .map { it.toUpperCase() }
    .forEach { println(it) }  
  ```

- 创建实例

  ```kotlin
  val rectangle = Rectangle(5.0, 2.0)
  ```

### 惯用语法

- 函数的默认参数

```kotlin
fun foo(a: Int = 0, b: String = "") { …… }
```

- 过滤 list

```kotlin
val positives = list.filter { x -> x > 0 }
```

或者可以更短:

```kotlin
val positives = list.filter { it > 0 }
```

- 检测元素是否存在于集合中

```kotlin
if ("john@example.com" in emailsList) { …… }

if ("jane@example.com" !in emailsList) { …… }
```

- 字符串内插

```kotlin
println("Name $name")
```

- 类型判断

```kotlin
when (x) {
    is Foo //-> ……
    is Bar //-> ……
    else   //-> ……
}
```

- 遍历 map/pair型list

```kotlin
for ((k, v) in map) {
    println("$k -> $v")
}
```

`k`、`v` 可以改成任意名字。

- 使用区间

```kotlin
for (i in 1..100) { …… }  // 闭区间：包含 100
for (i in 1 until 100) { …… } // 半开区间：不包含 100
for (x in 2..10 step 2) { …… }
for (x in 10 downTo 1) { …… }
if (x in 1..10) { …… }
```

- 只读 list

```kotlin
val list = listOf("a", "b", "c")
```

- 只读 map

```kotlin
val map = mapOf("a" to 1, "b" to 2, "c" to 3)
```

- 访问 map

```kotlin
println(map["key"])
map["key"] = value
```

- 延迟属性

```kotlin
val p: String by lazy {
    // 计算该字符串
}
```

- 扩展函数

```kotlin
fun String.spaceToCamelCase() { …… }

"Convert this to camelcase".spaceToCamelCase()
```

- 创建单例

```kotlin
object Resource {
    val name = "Name"
}
```

- If not null 缩写

```kotlin
val files = File("Test").listFiles()

println(files?.size)
```

- If not null and else 缩写

```kotlin
val files = File("Test").listFiles()

println(files?.size ?: "empty")
```

- if null 执行一个语句

```kotlin
val values = ……
val email = values["email"] ?: throw IllegalStateException("Email is missing!")
```

- 在可能会空的集合中取第一元素

```kotlin
val emails = …… // 可能会是空集合
val mainEmail = emails.firstOrNull() ?: ""
```

- if not null 执行代码

```kotlin
val value = ……

value?.let {
    …… // 代码会执行到此处, 假如data不为null
}
```

- 映射可空值（如果非空的话）

```kotlin
val value = ……

val mapped = value?.let { transformValue(it) } ?: defaultValue
// 如果该值或其转换结果为空，那么返回 defaultValue。
```

- 返回 when 表达式

```kotlin
fun transform(color: String): Int {
    return when (color) {
        "Red" -> 0
        "Green" -> 1
        "Blue" -> 2
        else -> throw IllegalArgumentException("Invalid color param value")
    }
}
```

- “try/catch”表达式

```kotlin
fun test() {
    val result = try {
        count()
    } catch (e: ArithmeticException) {
        throw IllegalStateException(e)
    }

    // 使用 result
}
```

- “if”表达式

```kotlin
fun foo(param: Int) {
    val result = if (param == 1) {
        "one"
    } else if (param == 2) {
        "two"
    } else {
        "three"
    }
}
```

- 返回类型为 `Unit` 的方法的 Builder 风格用法

```kotlin
fun arrayOfMinusOnes(size: Int): IntArray {
    return IntArray(size).apply { fill(-1) }
}
```

- 单表达式函数

```kotlin
fun theAnswer() = 42
```

等价于

```kotlin
fun theAnswer(): Int {
    return 42
}
```

单表达式函数与其它惯用法一起使用能简化代码，例如和 *when*{: .keyword } 表达式一起使用：

```kotlin
fun transform(color: String): Int = when (color) {
    "Red" -> 0
    "Green" -> 1
    "Blue" -> 2
    else -> throw IllegalArgumentException("Invalid color param value")
}
```

- 对一个对象实例调用多个方法 （`with`）

```kotlin
class Turtle {
    fun penDown()
    fun penUp()
    fun turn(degrees: Double)
    fun forward(pixels: Double)
}

val myTurtle = Turtle()
with(myTurtle) { // 画一个 100 像素的正方形
    penDown()
    for(i in 1..4) {
        forward(100.0)
        turn(90.0)
    }
    penUp()
}
```

- 配置对象的属性（`apply`）

```kotlin
val myRectangle = Rectangle().apply {
    length = 4
    breadth = 5
    color = 0xFAFAFA
}
```

这对于配置未出现在对象构造函数中的属性非常有用。

- Java 7 的 try with resources

```kotlin
val stream = Files.newInputStream(Paths.get("/some/file.txt"))
stream.buffered().reader().use { reader ->
    println(reader.readText())
}
```

- 对于需要泛型信息的泛型函数的适宜形式

```kotlin
//  public final class Gson {
//     ……
//     public <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonSyntaxException {
//     ……

inline fun <reified T: Any> Gson.fromJson(json: JsonElement): T = this.fromJson(json, T::class.java)
```

- 使用可空布尔

```kotlin
val b: Boolean? = ……
if (b == true) {
    ……
} else {
    // `b` 是 false 或者 null
}
```

- 交换两个变量

```kotlin
var a = 1
var b = 2
a = b.also { b = a }
```

- TODO()：将代码标记为不完整

Kotlin 的标准库有一个 `TODO()` 函数，该函数总是抛出一个 `NotImplementedError`。
其返回类型为 `Nothing`，因此无论预期类型是什么都可以使用它。
还有一个接受原因参数的重载：

```kotlin
fun calcTaxes(): BigDecimal = TODO("Waiting for feedback from accounting")
```
