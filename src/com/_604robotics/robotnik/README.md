# robotnik-framework

More documentation is forthcoming!

## Installation

To use this library in one of your robot projects, the best way course of
action would be to pull it in as a submodule.

 1. Assuming you're currently in the root directory of your project, and the
    source itself is in the `src/` directory, create the
    `src/com/_604robotics/` directory for the robotnik package to live in. On
    Linux or Git Shell, this should be something like:

        mkdir -p src/com/_604robotics/

 2. Again from the root directory of your project, assuming you're using git
    already (as you should be!), configure robotnik-framework as a submodule:

        git submodule add git@github.com:frc604/robotnik-framework.git src/com/_604robotics/robotnik/

 3. Switch over to the robotnik directory and pull in the code.

        cd src/com/_604robotics/robotnik
        git pull origin master

 4. Switch back to your project's root directory and commit your project with
    the submodule added:

        cd ../../../../
        git commit -am "Pull in robotnik-framework as a submodule"

After installation, you'll probably want to pull in new changes periodically,
in order to stay up-to-date with the latest version of the framework. To do
this:

 1. From the project root, change to the directory where the robotnik submodule
    lives:

        cd src/com/_604robotics/robotnik/

 2. Pull in the latest changes, if any:

        git pull

 3. If you get "Already up-to-date", then you already have the latest version.
    Otherwise, you'll need to re-commit your project with the new version of
    the framework. To do this, first, change back to the root directory of your
    project, and then do a `git commit`:

        cd ../../../../
        git commit -am "Update robotnik-framework to the latest version"

