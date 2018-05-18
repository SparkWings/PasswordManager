# Password Manager v1.0 - RELEASE #
The Java program that takes one password in order to show you all your passwords!

-----------------------------------


<h2>Introduction:</h2>
Have you ever had trouble remembering passwords? Have you ever been frustrated with yourself because you couldn't remember a password?
More importantly, have you ever wanted a program to manage your passwords, but didn't want to pay high dollar for one?

<b>Introducing the solution -- My Password Manager.</b>


<h2>How It Works:</h2>

This program contains the culmination of my programming abilities and security knowledge in order to provide you with a utliity program to store all of your passwords!

The program is written completely in executable Java (Not to be confused with JavaScript). It uses Java Swing in order to create a GUI for your viewing pleasure.

When the program first boots, you are prompted for your master password. Upon successful authentication, the program loads a table containing all your stored passwords for easy access. To lock the program, just close it! No worries about hackers gaining access to your data.

When you create a password, it is encrypted and stored in a file location known only to the program. For security reasons, the encryption key is also known only to the program. The files used to store the Password Manager's data and the application itself are kept completely separate from each other on your computer. Where you place the actual application will not affect configuration of the program -- it works for you!

For more help on how to use the program, feel free to use the Wiki tab for tutorials.

~ J








<h2>Programming Stats (For my fellow nerds):</h2>
- Encryption Algorithm: AES
- For security reasons, the key used for the Encryption and Decryption of data is known only to the program due to randomization. During program setup, keys and file locations are predetermined by the program itself.


- GUI: Java Swing


- IDE Used: Eclipse


- Java Version: 8


- Build Utility: Maven


- Dependencies Used:
    - JSON
    - ~AutoUpdater~ (DEPRECATED IN v1.0 RELEASE)
    
- In Development Since: 2017 (Constant: No, this is a boredom manifested application)
