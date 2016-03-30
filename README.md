# AndroidCarPAL 

CarPAL is an Android app that aims to show how we can simplify payment process for car related services such as fuelling and carwash.  This app is a proof of concept with limited functionalities such as Google play in- app payment, Google  place picker, NFC triggered app launch and automatic bluetooth pairing to access car music system. 

This app is an on-going experiment as part of our vision,  to implement NFC and mobile payment for services such as car-wash, fuel, etc by using google places location search and payment using android pay, visa pay through NFC reader terminals at service provider's location. Tap and pay would make life easier for our app users to make contactless payments at a carwash or gas station and also take advantage of NFC to automate mundane tasks while the user enters his/her vehicle.

According to a Deloitte report by end-2015, “five percent of the base of 600-650 million near-field communication (NFC) equipped phones will be used at least once a month to make contactless in-store payments at retail outlets.” The report states that “2015 will be an inflection point for the usage of mobile phones for NFC-enabled in-store payment, as it will be the first year in which the multiple prerequisites for mainstream adoption – satisfying financial institutions, merchants, consumers, technology vendors and carriers – are sufficiently addressed.”
App Monetization: 
We use freemium pricing strategy. ie, user downloads the app for free and monetization is made through transaction fees through the in-app payment.
CarPlay App Features:
NFC enabled bluetooth pairing - User enters the car and taps the NFC tag to sync to car’s bluetooth.
Google in-app billing - User selects a service  and is able to make payment through credit card/ debit card, redeem coupons and use paypal for purchases.
Google place picker - User is familiar with google search and is able to choose location through our app, to find local gas stations, car wash services and other places of interest.
Bluetooth pairing with car system - After initial one time pairing with the car music system, user is able to automate the task of connecting to bluetooth every time NFC is tapped. 
One click music - After pairing with the car music system, we have a button to connect to a custom music app of our choice(not fully functioning, but On-click it is able to play music file from local)

Tools/Platforms: 
We used the following tools:
Android Studio: for all coding purposes and designing the app’s layout 
Google Play Developer Console: for obtaining API keys for InApp Billing and Google places as well as creating Google Wallet merchant account for InApp Billing purposes
NFC Tag writer and Trigger app: To setup automatic pairing of bluetooth with music system and to launch the app

Future Enhancements: 
Looking into the future, we believe that following changes can be made in order to enhance our App’s functionality: 
Better UI design
Include Android pay and Visa pay as part of our payment 
Music App having Android beam functionality for android based car systems
Have our own web server and database to request web services from  movie theaters, restaurants, gas stations and car service stations after signing agreements with service providers to provide real time information
Agreements with car wash service and gas stations to incorporate NFC reader terminals for tap and pay
After user checkout we can provide the bill or receipt to the user’s mail
Our music on- click button will give user a choice to access music from pandora, youtube, spotify etc from one screen view.
