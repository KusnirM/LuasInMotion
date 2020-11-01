Architecture: MVVM Clean:
Reason: even it is small up, as company grows app will grow too. 
There will be new features, bug fixes. 
If you build good foundation based on good practices/ principles. 
You will be able to maintain, be able to add new features easier without changing the old code. 
MVVM clean brings less coupling, readability. and reflect client's/product's usecases.

Test coverage is at 85% after excluding presentation layer (Not much ui to test), di files, BuildConfig class,..
Last fixes were for BaseResponseMapper: missing few tests over there, 

Package system:
1.	data
    a.	di
    b.	mapper
    c.	model
    d.	repository
    e.	utils
2.	domain
    a.	di
    b.	model
    c.	repository
    d.	usecase
    e.	utils
3.	presentation
    a.	customViews
    b.	di
    c.	mapper
    d.	model
    e.	ui
        i.	base
        ii.	main
4.	exceptions
5.	utils

App


.DATA
•	represents source of the app: Local/Remote, in our case we do not need any local storage.

Di folders:
•	represents dependency injection per layer: hence divided into 3 modules:
dataModule/ domainModule/ presentationModule. 

As library I picked koin I can also use dagger 2 as compile time DI generator, 
but I find koin much simpler to use. 
It also adds up on readability and maintainability, 
while in dagger too there is much more coding.

Mapper folders: 
Used to transform/map models through the layers: While in data layer we get data, 
we need from the source we does not necessarily need exact data in other layers 
so we transform them into other models.

Data Repository:
Represents implementation of Domain Repositories:
If we would have also storage: It We could have Default DateSource which 
would be implemented by LocalDataSource and RemoteDataSource as domain layer should't know. 
(D from Solid principles: I will be very happy to explain it more detailed in next step :-).

Utils Folders:
1 in global for global utils/extensions: 
    Here should be extensions for globally used classes like
     “String”, “Collections”.. then in each layer are utils that should be used only in mentioned layer .

Challenges: 
I have to say all my last project were with retrofit and json responses. 
So, finding quickly the way how can I get response was the main one. 
But there came the advantage of MVVM clean where even I did not figure it out right away, 
I was not blocked by lacking implementation. I stubbed data source part 
and was working on other parts till I figured it out. 

Key classes are commented directly in files.
