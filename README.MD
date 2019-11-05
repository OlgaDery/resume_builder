# Resume Composer

## Content
- General Information
- Architecture
- Scaling
- Storing the data
- Testing

## 1. GENERAL INFORMATION

The application allows user to create a resume, to save it and to share via email/external app like **GoogleDrive**. It contains 3 functional features - *Profile*, *Positions* and *Education* - to add the blocks to the resume respectively. User can add new data blocks, modify existing and remove (excluding *Profile*, which is only editable). Each data block contains multiple parameters to specify (*title*, *location*, *start date*, *end date*, *description* etc). Some of the parameters are optional, some of them are required (those are marked with the specific icon).

Compatible with **API** level **21+**.
French localized strings added.

No permissions required.

## 2. ARCHITECTURE

The application is following the MVVM general standarts. There is one **Application** class, one **Activity** class responsible for navigation, multiple **Fragments**, one **ViewModel** class(with shared and observable data) that is mapped to the **Activity**. To access the instances of some service classes and also the **Application Context** object **Dagger2** library is being used. To map the data to the views, android databinding (one way and two way) is being used. To handle the asyncronious calls, **kotlin coroutines libraries** are being used.

## 3. SCALING
The app is designed to allow the highest level of scalability, that includes both UI and data model/functionality. For UI, there are multiple reusable classes that can be easily configured to represent different objects (**ViewData** class, all the **Record** fragments). The data model is being set up the way that the data can be stored in SQL database without loosing the important relationships (id of the *Profile* is being stored in all the related records, what would easily allow to constract the **Resume** out of profile and all the records. 

## 4 STORING THE DATA
Data is being stored in **SecuredPreferences** as encrypted *JSON* string (the source for json is a **Resume** object that accumulated the data from other classes). For encryption **Tink** library is being used.

This approach has been considered due to the following circumstances:
- Comparing to the database (SQL and noSQL), it is very easy to set up and to configure. Reading the heavy data/big collections may be too long, 
but for this application it does not seem to be a case. Though, with more complex data model/bigger amount of data using the database may be the best choice.

- Comparing to the file, it is faster to read/write. Potentially it may be done withing UI thread. In current business logic, the data is being written to the file only if user is clicking "share" button so this file could be shared with external app. Later this file can be removed.

## 4. TESTING
UI navigation and components are being tested with **Espresso**. Tests may be run in any order. For Unit testing, besides standart junit library, mockk library is used for coroutines testing, and mockito for mocking objects.
