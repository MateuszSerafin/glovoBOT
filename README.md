# GlovoBOT
# What was it
It was a bot to book automatically hours on your courier account on glovo. They probably changed their API. This is probably not working at this point.
It was working as of march 2023. The issue is it's pretty useless due to fact that couriers that already have their rating receive hours 30 minutes before others. 
Also of course this is against terms of service but while i was testing it i seen that hours dissapear before my bot does something (you can't do it faster in GUI) meaning that other couriers were botting too Lol.
## Updating code guide
I provide base code, they can update their API whenever. You can reverse their API again and update my code or change it for your needs

You need of course courier account, While updating this guide i did not have access to this account. So i couldn't verify if i removed emulator checking correctly. (Emulator check is after login, so you can still run app on emulator but the issue is after login)

Prepare emulator for android (I used android studio) and mitmproxy.

1. Get .apk on linux machine, install android studio, choose debug apk option, then i did clear text search
![search](./sample-images/search.png)
2. (Optional) If you need to look at java files <br> run ```apktool d apk.apk``` <br>
Then inside of created directory <br>
run ```d2j-dex2jar classes.dex classes2.dex classesN.dex``` it will create .jar files that you can decompile and then index in idea and perform static analysis. <br>
![decompiled](./sample-images/decompiled.png)
3. run ```apktool d apk.apk``` it will decompile each class into .smali file. I don't have current account to do it properly long story short you need to skip emulator check using this file, last time i did it i had to just jump one if statement. 
![smali](./sample-images/smali.png)
4. Edit AndroidManifest.xml

3.You need to decompile it using apktool. You need to look through the decompiled code. There is an emulator check. In my case i found it through resources it was totally unobsfucated and i just followed resource. You need to edit .smali file (compiled java byte code) in version of app i used it was just if satement to remove, I didn't know what i was doing it took me like 30 minutes, you can find it too. Then compile it back again using apktool.

4.Import it to android studio make sure to edit manifest file to allow third party certificates (I forgot the name of this option again 30 minutes you can do it.)

5.You should be able to see traffic decrypted.

From my testing some parameters such as deviceID can be random and it still works they probably just use it for tracking. Not sure if they have a big red alert if something is not right. 


### Why I post it outdated 
I want to keep it as a resource for others. 