
package cn.todev.examples.pattern;

public class FactoryMethodPattern {

    public static void main(String[] args) {
        Factory factory = new ConcreteFactory();
        Product concreteProduct = factory.factoryMethod();
        concreteProduct.use();
    }

    static abstract class Product{
        abstract void use();
    }

    public static class ConcreteProduct extends Product {
        @Override
        public void use() {
            System.out.println("use - ConcreteProduct");
        }
    }

    static abstract class Factory{
        abstract Product factoryMethod();
    }

    public static class ConcreteFactory extends Factory {
        @Override
        public Product factoryMethod() {
            return new ConcreteProduct();
        }
    }

}