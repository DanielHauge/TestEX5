# Test Assigment (Midterm assignment)
This repository is a exercise project for Software development (PBA) Test course. Daniel (cph-dh136)

## Description
This is a midterm assignment which is a little larger than usual exercises. This assignment sets out to demonstrate an understanding of what the purpose of testing is, what parameterized tests is and how its used, what data driven testing is and why it often makes sense to read test data from a file and more. The assigmment is comprised of 3 projects as a whole and are based on this ressource: [Here](https://github.com/datsoftlyngby/soft2018spring-test-teaching-material/blob/master/exercises/Midterm%20AssignmentNEW.pdf)

## Assignment



### Project 1
This project is a plate recognition application. The project has 8 bullet points to complete. Therefor i will go through these with notes on the way.
#### Build & Run
First things first. Importing of the project to my chosen IDE. I've chosen IntelliJ from jetbrains, this is purely out of preference, but also the strength and tools available in the IDE. I cloned the repo and moved all the files to a new folder in this repository and added it as an existing module build from maven, and simply build the application with ```clean install -DskipTests``` and then ``` exec:java ```. As shown below:
[![https://gyazo.com/c9df2d8ec82d9c25a742457715d2bf41](https://i.gyazo.com/c9df2d8ec82d9c25a742457715d2bf41.png)](https://gyazo.com/c9df2d8ec82d9c25a742457715d2bf41)

I first stumbled upon an error on test_012.jpg. My guess is that it might have trouble with the new european plates, mistaking the european bagde as an "I" and then it proceeds to fail afterwards, which might be becourse of 2 things: 
- Limitations in the licensen plate, which means the last "charactor doesn't include". ie. It might have a maximum of 7 signs including up to 3 letters and 4-5 numbers.
- Mistaking the "A" for a 4, because of limitations in the number of chars compared to numbers perhaps. ie. maybe there is a restriction of a maximum of 3 letters, and therefor mistake the last A as a 4.

Because i was curious, i wanted to try find another car with the european badge to see if my hypothesis was correct. I then stumpled upon test 19, which has errors on the letters R and K, these letters are easily mistaken to KY. So it might not be good with these letters i most assume. Allthough i've found plenty of examples where it can easily handle RK.
Further more i found a couple of other cars with the european badge, these doesn't seem to be a problem.

I then scoured alot of them, and observed the errors typically occur if the image is a little smooshy (Unclear) or alot of reflection in the shot. And the errors typically occurs with charectors than can be easily mistaken for another. Like 6 and 8. K and Y, R and K etc. Sometimes it acts realy weird rarely, not even recognizing very clear plates or put in an extra letter or number for no apperent reason.

From running the program manually in a manner which i would call System testing, I noticed the application are not functioning as intended, and also gathered alot of information about what might be wrong, and what i might need to look for.

#### Localize tests
Tests, can be found in the test folder. In IntelliJ, it is quite easy to distinguish between tests in the same suit. So to put in the Logger.info command wasn't realy neccesary for me. But i did it anyway for good meassure. Can be seen below.
[![https://gyazo.com/886d927a70298d1d409ab4445a5b927d](https://i.gyazo.com/886d927a70298d1d409ab4445a5b927d.png)](https://gyazo.com/886d927a70298d1d409ab4445a5b927d)

After getting familiar with the test codes, i most admit the data driven approach is very nice. It basicly takes all files within the folder, and itterate thought them and using a property file to state the answers. This test would essentially not need any rewriting for new pictures/ new data. If just providing with new pictures and property file. However, as it is now, it will not state which images it is having a hard time with. This is an issue to pinpoint where the recognizer is having a hard time. The second test only succeds, because it is only expected to succed 53 of the times. Depending on the end goal, this should bechanged. If we assume we want 100% or so close you can define it as 100%, we should test each individual image and have them all pass. To achive this, parameterized testing can be used.

#### Fixing the tests
Since i'm lazy. I'm using the JUnit.jupiter.params library with Csv as source. I've used this site as a good reference: https://blog.codefx.org/libraries/junit-5-parameterized-tests/. I quickly just replaced all '=' signs with a comma, and renamed the file extension to .csv. 
And did the following code: 

```java
@ParameterizedTest(name = "{0}")
    @CsvFileSource(resources = "/results.csv")
    void TestSnapShots(String image, String expected) throws Exception {
        logger.info(image+ " Should be "+expected+"\n\n");
        ...// Single intelligent test here, with a slight modification to actully use the parameters.
```
[![https://gyazo.com/03349f6418f70807d27abf54101b6bd3](https://i.gyazo.com/03349f6418f70807d27abf54101b6bd3.png)](https://gyazo.com/03349f6418f70807d27abf54101b6bd3)
To clarify, it is actully pretty easy just to change the delimiter to "=" aswell with @CsvFileSource(), but i thought i wanted a idiomatic approach to .csv solution.
#### Reflections
As stated before, the second test or the one which was just made is data driven. This made me think that it is very good to write datadriven tests where available. Reasoning for this, is it is much easier to test huge amounts of configurations, and data without having to write new tests, nor rewrite the tests if we need different test cases. Here we could essentially just replace the pictures and the expected results list, and the test would run as inteded without doing anything to the test. No rewriting, no revisiting, it just works.
Another thing is whether or not this test is a true unit test or not. I think in daily usage of the term Unit test, i think it could pass. But the true conceptual idea of a unit test, is to isolate some code, module, object and test upon that only, while having no dependencies on other things that needs to work. While this specific test touches the file system for a csv file, which means it breaks the idea. Also uses 2 objects CarSnapShot and Intelligence. To isolate better to have fully isolation, we can stub or mock some elements to realy isolate it's behavior. I think you would be able to pass this test off as a system test. Depending on requirements, it is testing the whole systems behavior, ie. to recognize car plates. But could also be argued as integration testing, ie. testing something where modules are combined to test whether or not they work together to furfill requirements, if we count the GUI as being apart of the system too it wouldn't be system testing.

My suggestions to solve this problem, is to test on individual parts in a isolated manner without dependencies towards other things. If the point was to do specificly unit testing as the true conceptual unit testing.

However i still think the data driven approach is very smart and nice. It allows for so much more testing without having to write as much hardcoded tests. Even if the tests become integration tests because it touches the file system.

#### Hamcrestifying the tests
Lets make the tests look nicer, and easier to read. First up the RecognitionAll which was made previusly. Now it looks like this:
```java
@ParameterizedTest(name = "{0}")
    @CsvFileSource(resources = "/results.csv")
    void TestSnapShots(String image, String expected) throws Exception {
        logger.info(image+ " Should be "+expected+"\n\n");

        CarSnapshot carSnap = new CarSnapshot("snapshots/"+image);
        assertThat(carSnap, is(not(nullValue())));
        assertThat(carSnap.getImage(), is(not(nullValue())));
        Intelligence intel = new Intelligence();
        assertThat(intel, is(not(nullValue())));
        String result = intel.recognize(carSnap);
        assertThat(result, is(not(nullValue())));
        assertThat(result, equalTo(expected));
        carSnap.close();
    }
```
This made the test very declaretive, ie. state what we want rather than what we need to do to get what we want.

... More hamcrest JUnit 5.x tests or not?

#### Conclusion to project 1
To summerize, This project has alot of semi working features and tests that tests if it works as inteded or not. 
The initial tests looks as if everything works as inteded, but delving deeper into it, it actully shows that 53 of data-written test cases works and not all. 
The inital test does not expose which test is faulty. That is what my tests sets out to do, with parametermized testing. 
To create test with parameters, where i used a CSVfile as source. Running through all test cases and using that as arguments into the test, dynamicly creating tests as there is test cases in the CSVFile.
To figure out which test actully fails, and which passes. The purpose of testing is to verify and validate that the system meets the stated requirements or specifications, while also finding defects and bugs and ensuring/increasing quaility. ie. We want to have the tests fail, if the system doesn't do as we want it to do. eg. We want the pictures which it faulty recognize to be failing in the tests. And not get pass because we only want 53 of the test cases to pass, nor get fail on all because only 1 picture was faulty.

In addition i added hamcrest to the project. Making the tests declaritive instead of imperative. That way it is easier to read and understand what the test does and tests for.

All code and tests can be found in the [Project 2 module]()

--------------------------------------------------------

### Project 2
This project is about Unit Testing, Testable Code, Mocking and Code Coverage. This project is taking the opposite approach than what is idiomatic. ie. We have code that is untested, now we want to test it.

##### Setup and getting started.
I git cloned the project down and moved it into my own repository. Added the module and added maven framework, build and ran the main method. However it is not running smoothly.
[![https://gyazo.com/3d741147b726de4f6c7c7cafd785d115](https://i.gyazo.com/3d741147b726de4f6c7c7cafd785d115.png)](https://gyazo.com/3d741147b726de4f6c7c7cafd785d115)

To fix this issue i found a article on stackoverflow regarding old JDK versions and how they dont aggregate modules the same way. It is the JAXB API that isn't in the default java 9 any longer. I fixed it with some maven dependencies. [Article](https://stackoverflow.com/questions/43574426/how-to-resolve-java-lang-noclassdeffounderror-javax-xml-bind-jaxbexception-in-j)


First up, i would refractor the code by first creating interfaces for the classes and having the current code as a temp implementations. This will also provide good oppotunity to create and use them as mocks later on.

I avoided making interfaces for Joke, Jokes and JokeException. They were simply to simple and i didn't want to waste to much time with them. Also they don't do much other than contain data/information. Very tedious and unhelpfull to test that, "When i set a value on this object, is the value realy set to what i stated?"

While making the interface, i noticed a lot of smelly things regarding the code. To write good testable code, we need to follow the SOLID principles. ie.
- Single responsibility principle (A class should only have a single responsibility)
- Open/closed Principle (Meaning, that the code should be open for extensions, but closed for modifications)
- Liskov substitution principle (Objects should be replaceable with instances of their subtypes and still maintain correctness, functionalwise. ie. Rest of the software doesn't need to be rewritten, because of the replacement)
- Interface segregation principle (meaning, many interfaces are better than one general purpose one).
- Dependency inversion principle (Meaning, shifting the responsbility of dependencies away from the object, to reduce it's responsability, ie. Do Inversion of controll and dependency injection).

Furthermore, there are even more concret guidelines on these resources: [Lecture slides](https://github.com/datsoftlyngby/soft2018spring-test-teaching-material/blob/master/slides/UnitTestingRecap.pdf) and [Article](http://misko.hevery.com/code-reviewers-guide/)

##### Dateformatter

The code isn't pretty, nor very testable according to previus mentioned principles. Filled with static methods and dependencies instantiated at call and more. I changed the DateFormatters responsibility to only do date formatting. With the following implementation
```java
@Override
    public String getFormattedDate(String timeZone, SimpleDateFormat simpleFormat, Date date) throws JokeException {
        if(!Arrays.asList(TimeZone.getAvailableIDs()).contains(timeZone)){
            throw new JokeException("Illegal Time Zone String");
        }
        simpleFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return simpleFormat.format(date);
    }
```
Now the formatter is not responsible for the SimpleDateFormat object, only check if format given is correct, and use the formatter to format it. Also doesn't depend on the date object any longer. So it doesn't have the responsability to figure that out or make that. In case another constructor for date is wanted. (Default constructor of date, is basicly just taking current time as new date.)

With the first task h1). It was tasked to create tests to verify getFormattedDate methods public behavior. It had some issues with mockito and this made me choke. It forced me to rethink how and what to mock. I initially understood, the concept of wanting to test the behavior of the method. ie. To test if the method was called and with what objects and such. But mockito was acting up, based on my inexperience with mockito. Errors such as "This is not a mock object". Reasoning is, that i refractored the class to not depend on date and simpleDateFormatter anylonger. I was trying alot of things, like also mocking the class that i needed to test, which i later figured out was not a good idea. We do not mock the SUT (System under test), if we mock that, we don't realy test it. We need to mock/stub/face etc. the surronding objects which the SUT depend on. The solution i ran with, was to "Spy" the object "simpleDateFormatter" this way, i can test with the actual object instead. This worked out decently.

See [DateFormatterInterfaceImplTest]()


##### JokeFetcher 1. Refactoring
Furthermore, i refractored Jokefether implementation because of this change. By using inversion of control and dependency injection.
```java
    private DateFormatterInterface df;
    public JokeFetcherInterfaceImpl(DateFormatterInterface DF) {
        df = DF;
    }
```
And also made a main class to implement this object.
```java
public class Main {


    public static void main(String[] args) throws JokeException {

        JokeFetcherInterfaceImpl JF = new JokeFetcherInterfaceImpl(new DateFormatterInterfaceImpl());
        Jokes jokes = JF.getJokes("eduprog,chucknorris,chucknorris,moma,tambal","Europe/Copenhagen");
        jokes.getJokes().forEach((joke) -> {
            System.out.println(joke);
        });

        System.out.println("Is String Valid: "+JF.isStringValid("edu_prog,xxx"));
    }
}
```

Testing on the JokeFetcher class. I noticed something pretty good advantage of doing it like this. By doing it this way, you are completely independent of the state of a dependent components implementation. You don't need to know how the implementation is progressing or anything about the dependent components. You have the interface, and you can stub away all components, meaning other teammembers can work on the implementation independently of working on this test and implementation of the SUT. ie. DateFormatterInterfaceImpl didn't need to work or be implemented for the JokeFetcher testing can begin.

##### Refractoring for Polymorphism

While refractoring the code to be more testable, i noticed all the tests i've done up to this point has been invalidated. This made me think, that allthough testing gives quaility and ensures the code works as inteded, it also comes with a price which is additional workload. In some cases it increases dramaticly. I will be forced to refractor all the tests aswell, after the refractoring of the code. Eg. It is now the FetcherFactory that is responsible for AvailableJokeTypes.

I also refractored the Original JokeFetchers name to JokeMaster. This has been done for me to have a better overview, since i thought it became a little convoluted. 

As said in the guided tour. Polymorphisism is quite impressive in it's workings. However, i have also come to the conclusion that it is quickly becomming very convoluted, and complicated to hold onto the complete systems overview, if not structured very, **VERY** well. But the gains are quickly there to observe. To work with generic list of "Interfaces" where it might be a EduJoke object, or maybe a Moma object or Tambal object is realy cool and usefull aswell, since we can change the individual classes functionality and system will still work, aswell as tests without having to refractor it.

##### Test Coverage 

Having run test coverage on the tests with both IntelliJ's built in coverage tool and also Jacoco. It shows 100% all across the board.

[![https://gyazo.com/0b5a68ce7d2be18637fb77f060d5a763](https://i.gyazo.com/0b5a68ce7d2be18637fb77f060d5a763.png)](https://gyazo.com/0b5a68ce7d2be18637fb77f060d5a763)

As can be seen, the tests reflects all methods being run and all lines of code being touched in the tests. I've come to think, that these numbers are quite easy to obtain because the joke application is very simple and not too big. In a case where there would be alot more code, i can see why you'd aim for roughly 80% because some of the tests seem very exhaustive. ie. Why even bother testing the method "GetAvailableTypes". There's one line of code ```return AvailableTypes;```. Which can easily be skipped in testing in my opinion.

#### Conclusion to Project 2
To summerize, this project started out with pretty untestable code. I had to refractor the code according to SOLID principles, to make it more testable. Thereafter i could test with java's testing framework JUnit. In addition i used Hamcrest to make the tests declaritive and therefor more readable, visibile and understandable. I used mockito to handle dependencies which might not be working yet, also to isolate the SUT and for testing controll flow / behavior of the classes in question. Also used JaCoCo and IntelliJ code coverage tools for measuring how much of the code was tested.

This project needed to be refractored to follow the SOLID principles before testing could realy be done. eg. By using Factory pattern, depending on interfaces and polymorph implementations into the dependencies at use (depending on abstration, not concretions), by limiting the responsibility of a class to a single responsbility, having the classes be open for extension, but closed for modification. etc.

All code and tests can be found in the [Project 2 module]()

--------------------------------------------------

### Project 3
This project is about Test Driven Development, Mocking practise. The project is to build a monopoly game by using test driven development (Behavior Driven Development) as a methodology / Development process. ie. Tests/Behaviors first.

##### Setup and getting started.
I've created a new maven module from the new release of java. JDK 10. (I don't believe it brings much change, but i thought why not jump on the train of the new), i've included Mockity, JUnit 5, Hamcrest into the pom file, and now i'm ready to create the monopoly.

#### Design
First up, a quick draw of the systems overview. I've drawn UML in software idea modeller for quick reference.

[![https://gyazo.com/c73da40537965370ecdbe25aa5654f01](https://i.gyazo.com/c73da40537965370ecdbe25aa5654f01.png)](https://gyazo.com/c73da40537965370ecdbe25aa5654f01)

This is the initial design of the project.

#### Interfaces
I thought, before i began the development, i would start with the interfaces, so i can use them in the tests and so on.

See these interfaces

- [IBank]()
- [IBoard]()
- [IDie]()
- [IGameHandler]()
- [ILocation]()
- [IPiece]()
- [IPlayer]()

Some afterthought has changed the Interfaces, so it wont look 100% like the drawing.

#### Player
First up, i would test and design the take turn function. What i essentially want is the following.
- Player rolls dice and get random number
- Then move the players piece that amount forward
- Then either buy or endturn

This functionality depends on allmost every single class. Hereby mockito comes in to mock these objects. In addition, they are not even implemented yet, so to test we need to mock it anyways.

First i will make the implementation and test IPlayer while mocking all dependencies.

The test is mocking alot of objects and verifying it's behavior. Here is a sneak peak of one of the tests:
```java
IDie[] dice = {die1, die2};

        given(piece.GetLocation()).willReturn(currentLoc);
        given(currentLoc.GetBoard()).willReturn(board);
        given(board.GetNewLocation(eq(currentLoc), anyInt())).willReturn(newLoc);
        given(die1.Roll()).willReturn(5);
        given(die2.Roll()).willReturn(5);

        IPlayer player = new PlayerImpl("Daniel", 100, piece);
        player.TakeTurn(dice);

        verify(die1).Roll();
        verify(die2).Roll();
        verify(piece).GetLocation();
        verify(currentLoc).GetBoard();
        verify(board).GetNewLocation(eq(currentLoc), anyInt());
        verify(piece).Move(newLoc);
```

By this, the player is done. Behavior is tested, even if no other dependencies has been implemented. See [PlayerImplTest]()

[![https://gyazo.com/3cc5608f650331d4bc524c14e00b0d7c](https://i.gyazo.com/3cc5608f650331d4bc524c14e00b0d7c.png)](https://gyazo.com/3cc5608f650331d4bc524c14e00b0d7c)

#### Die
I don't realy want to test the die. It is simple and i would argue testing on it is exhaustive testing. But for purpose of practise i will. Below is the implementation

It doesn't depend on anything, nor does it have complicated

```java
public int Roll() {
        return (int)(Math.random() * 6) +1;
    }
```

See [DieImplTest]()

#### Rest of the system
From here on, i will move forward with the rest of the system. Behavior testing and implementing it like with player.

Now all except the gamehandler / main method has been tested and implemented.

[![https://gyazo.com/63434375d6e6c9b2ecc693e0cce4080e](https://i.gyazo.com/63434375d6e6c9b2ecc693e0cce4080e.png)](https://gyazo.com/63434375d6e6c9b2ecc693e0cce4080e) 
[![https://gyazo.com/3637f7b9e14dcf095392de6b1888295b](https://i.gyazo.com/3637f7b9e14dcf095392de6b1888295b.png)](https://gyazo.com/3637f7b9e14dcf095392de6b1888295b)

See implementations:
- [BankImpl]()
- [BoardImpl]()
- [DieImpl]()
- [LocationImpl]()
- [PieceImpl]()
- [PlayerImpl]()

See tests:
- [BankImplTest]()
- [BoardImplTest]()
- [DieImplTest]()
- [LocationImplTest]()
- [PieceImplTest]()
- [PlayerImplTest]()

I must admit, that there were alot of cases where i thought it was definitly not neccesary to do testing. eg. the Piece, basicly just holds a information about on what location it is on. I would argue this doesn't need testing.
```java
public class PieceImpl implements IPiece {

    private ILocation location;

    public PieceImpl(ILocation location){
        this.location = location;
    }


    public void Move(ILocation location) {
        this.location = location;
    }

    public ILocation GetLocation() {
        return this.location;
    }
}
```
This concept kinda crept up more places. Where i thought, this is a little unneccesary to test extensively. Where as the player for example, is more important and brings more functionalities to test.

#### Putting it all together
I made final touches to the system, and made a static main method to assemble all the components together and run an example game. Also put in System.out.print() lines into a few places to add console visibility to what is happening. Here is the main method:
```java
public class GameHandlerImpl implements IGameHandler {


    static List<IPlayer> pl;
    static IBoard board;
    static IDie[] dice;


    public static void main(String[] args){


        board = new BoardImpl();
        pl = ConstructPlayers();
        dice = new IDie[]{new DieImpl(), new DieImpl()};

        for (int i = 0; i < 10 ; i++) {

            for (IPlayer p : pl) {
                System.out.print("TURN START: #"+i+" - ");
                p.TakeTurn(dice);

                /// If Player has enough money, buy location.
                /// If Player lands on location that is owned by someone -> Pay Player, if not enough money Loan money from bank
                /// If Player passes the line -> Get money
            }
        }
    }
    
    public static List<IPlayer> ConstructPlayers(){
        ArrayList<IPlayer> players = new ArrayList<IPlayer>();
        for (int i = 0; i < 5 ; i++) {
            players.add(new PlayerImpl("Player"+i, i,new PieceImpl(board.GetLocation(0))));
        }
        List<IPlayer> results = players;
        return results;
    }

}
```

Output snippit trace:
```
... etc.
TURN START: #4 - Player: Player3 - Rolled: 6 - Location: #37 -  ENDTURN
TURN START: #4 - Player: Player4 - Rolled: 12 - Location: #44 -  ENDTURN
TURN START: #5 - Player: Player0 - Rolled: 9 - Location: #39 -  ENDTURN
TURN START: #5 - Player: Player1 - Rolled: 5 - Location: #2 - Passed the line -  ENDTURN
TURN START: #5 - Player: Player2 - Rolled: 9 - Location: #42 -  ENDTURN
TURN START: #5 - Player: Player3 - Rolled: 7 - Location: #44 -  ENDTURN
TURN START: #5 - Player: Player4 - Rolled: 10 - Location: #4 - Passed the line -  ENDTURN
TURN START: #6 - Player: Player0 - Rolled: 7 - Location: #46 -  ENDTURN
TURN START: #6 - Player: Player1 - Rolled: 7 - Location: #9 -  ENDTURN
TURN START: #6 - Player: Player2 - Rolled: 7 - Location: #49 -  ENDTURN
TURN START: #6 - Player: Player3 - Rolled: 5 - Location: #49 -  ENDTURN
... etc.
```


#### Conclusion to Project 3
To summeraize, this project has been built with Test driven development methodology (Behavior driven), which has resulted in heavy use of Interfaces, polymorphisms and dependency injection.
This has been crucial for testing purposes, ie. To test classes in isolation in addition to have each test be indepedent of it's dependent classes development status. This has made the whole system very loose coupled, since it is quite easy to modify or extend the current system. eg. If we wanted an additional method, we can extend the class, or create a new interface which extends from the previus interface. This will make it possible to change/modify a single class without having to worry about destroying the rest of the system.

I believe that in some cases, there is no need to test surden features. eg. Some objects that only contains data like the Piece object, that only contains a location and methods for setting and getting that location. But in case of test driven development, we will automaticly go and test all code, since it is apart of designing the system.

Choosing the right test techniques realy depends on what is being tested. I would argue, that testing a die which has a roll method that returns a random integer between 1 and 6. I think a black-box input-output test is fine (eg. boundary testing, assert 100 rolls is between 1 and 6, not lower or higher.). If there's no requirements on how the specifics of how it finds that random integer is stated, then i don't care about what happens, just what input and output will be. However in the case of the Player class, which has alot more business logic and important / critical aspects to the system.
I believe it is alot more important to test. For one, it is likely to influence the whole system dependin on it's quality and durability, and will likely go though alot more lines of code with influence the test coverage percentage.

This project also realy hammered home my understanding on the differences between black-box and white-box testing. As can be argued white box testing can also be called clearbox testing. That blackbox testing is testing only input and outputs, eg. with blindfolds and seing what happens. Where as white box testing is testing things in the clear, eg. Controll flow as in testing what behaviors is going on inside the SUT and such.

All code and tests can be found in the [Project 3 module]()