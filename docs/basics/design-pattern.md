# 设计模式

## 目录

- [创建型](#创建型)
  - [建造者模式](#建造者模式(Builder))
- [结构型](#结构型)
- [行为型](#行为型)


## 创建型

### 工厂模式(Factory)

### 单例模式(Singleton)

### 建造者模式(Builder)

UserBuilder
```java
public class User 
{
    //All final attributes
    private final String firstName; // required
    private final String lastName; // required
    private final int age; // optional
    private final String phone; // optional
    private final String address; // optional
 
    private User(UserBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.phone = builder.phone;
        this.address = builder.address;
    }
 
    //All getter, and NO setter to provde immutability
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public int getAge() {
        return age;
    }
    public String getPhone() {
        return phone;
    }
    public String getAddress() {
        return address;
    }
 
    @Override
    public String toString() {
        return "User: "+this.firstName+", "+this.lastName+", "+this.age+", "+this.phone+", "+this.address;
    }
 
    public static class UserBuilder 
    {
        private final String firstName;
        private final String lastName;
        private int age;
        private String phone;
        private String address;
 
        public UserBuilder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
        public UserBuilder age(int age) {
            this.age = age;
            return this;
        }
        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }
        public UserBuilder address(String address) {
            this.address = address;
            return this;
        }
        //Return the finally consrcuted User object
        public User build() {
            User user =  new User(this);
            validateUserObject(user);
            return user;
        }
        private void validateUserObject(User user) {
            //Do some basic validations to check 
            //if user object does not break any assumption of system
        }
    }
}
```
Use
```java
public static void main(String[] args) {
    User user1 = new User.UserBuilder("Lokesh", "Gupta")
    .age(30)
    .phone("1234567")
    .address("Fake address 1234")
    .build();
 
    System.out.println(user1);
 
    User user2 = new User.UserBuilder("Jack", "Reacher")
    .age(40)
    .phone("5655")
    //no address
    .build();
 
    System.out.println(user2);
 
    User user3 = new User.UserBuilder("Super", "Man")
    //No age
    //No phone
    //no address
    .build();
 
    System.out.println(user3);
}
```
OutPut:
```text
User: Lokesh, Gupta, 30, 1234567, Fake address 1234
User: Jack, Reacher, 40, 5655, null
User: Super, Man, 0, null, null
```


## 结构型

### 适配器模式

### 桥接模式

### 装饰模式

### 代理模式


## 行为型

### 观察者模式

### 策略模式