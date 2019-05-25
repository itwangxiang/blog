// IOSSManager.aidl
package cn.todev.examples.service;

// Declare any non-default types here with import statements

interface IOSSManager {

    List<String> queryList();

    void push(String oss);

}
