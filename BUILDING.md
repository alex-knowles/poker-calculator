## Build instructions

1. Install [Gradle 2.3](https://services.gradle.org/distributions/gradle-2.3-bin.zip)
2. `gradle build`

## Other useful commands

* `gradle test` -- runs automated tests
* `gradle checkstyleMain` -- checks main classes for style violations
* `gradle checkstyleTest` -- checks test classes for style violations
* `gradle check` -- runs automated tests and checks all classes for style violations
* `gradle tasks` -- print a summary of all available tasks

## Eclipse IDE

The PokerCalculator project can be imported into a new Workspace in Eclipse with minimal effort.

### Import Project into a New Workspace

**Note:** Before opening Eclipse, run `gradle build` at least once to ensure that third-party libraries are downloaded and ready for Eclipse.

1. Create a new Workspace in Eclipse
2. **File** -> **Import...** -> **Existing Projects into Workspace**
3. Select the root directory where the repo is cloned to import the `main` and `test` classes
4. Open Eclipse **Preferences**
5. Navigate to **Java** -> **Build Path** -> **Classpath Variables**
6. Select the **New...** button, and then define `GRADLE_USER_HOME` with the path to your `.gradle` directory
7. If prompted, do a full rebuild.  The project should now build successfully!

### Import code style formatter

This project uses Checkstyle to maintain code style.  Style rules are defined in:

`/config/checkstyle/google_checks.xml`

To import a companion Eclipse Formatter into your Workspace:

1. Open Eclipse **Preferences**
2. Navigate to **Java** -> **Code Style** -> **Formatter**
3. For **Active profile**, select the **Import...** button
4. Select the XML file at:

`/config/eclipse/GoogleStyle.xml`

Now you can use **Source** -> **Format** to automatically format your changes!

### Organize imports

The Checkstyle rules (mentioned above) also specify in which order `import` statements should be sorted.  To match these rules, do the following:

1. Open Eclipse **Preferences**
2. Navigate to **Java** -> **Code Style** -> **Organize Imports**
3. Arrange the package prefixes like so:
  1. com
  2. org
  3. java
  4. javax

Now you can use **Source** -> **Organize Imports** to automatically sort your `import` statements!

### Adding, removing, or changing dependencies

If you need to change the third-party libraries on which PokerCalculator depends, you will have to first modify the `build.gradle` configuration file.  For example, if you wanted to add a new dependency, use a newer version of an existing one, or remove a dependency altogether.

Once the `build.gradle` changes have been made, you may need to run `gradle eclipse` to propogate those changes into your Eclipse workspace.  Any resultant changes to files such as `.classpath` should be committed to version control.
