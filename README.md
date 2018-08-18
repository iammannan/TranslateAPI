# TranslateAPI
Simple Java library to translate your text using Google Translate without using of API KEY

# Screenshot
![alt text](https://raw.githubusercontent.com/iammannan/TranslateAPI/master/demo2.png)

# Download

* Step 1. Add it in your root build.gradle at the end of repositories:
```java
    allprojects {
        repositories {
          ...
          maven { url 'https://jitpack.io' }
        }
    }
```
* Step 2. Add the dependency
```java
   dependencies {
	        implementation 'com.github.iammannan:TranslateAPI:1.1'
	}
```
  * Full Code - Example
  ```java
    	TranslateAPI translateAPI = new TranslateAPI(
                        Language.AUTO_DETECT,   //Source Language
                        Language.TAMIL,         //Target Language
                        "Your Text");           //Query Text

                translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
                    @Override
                    public void onSuccess(String translatedText) {
                        Log.d(TAG, "onSuccess: "+translatedText);
                        textView.setText(translatedText);
                    }

                    @Override
                    public void onFailure(String ErrorText) {
                        Log.d(TAG, "onFailure: "+ErrorText);
                    }
                });
 ```
![alt text](https://raw.githubusercontent.com/iammannan/TranslateAPI/master/demo1.png)

