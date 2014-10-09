# Pulling the Code

 1. [Set up][1] and [configure][2] your Netbeans and FRC tools installation.

 2. Download and install [git][3] for your system.

 3. On Linux-based systems, open up a terminal; on Windows, find "Git Bash" in
    the Start Menu and open it up.

 4. You'll start out in your home directory. You'll need to switch over to your
    "NetbeansProjects" directory, which is usually in your "Documents" folder.
    Run the following command inside your console window:

        cd Documents/NetbeansProjects
 
 5. Pull down a copy of this repository by running the following commands:

        git clone git@github.com:frc604/FRC-2014.git
        cd FRC-2014/
        git submodule init
        git submodule update
        cd ..
        mv FRC-2014 2014

NOTE: If you recieve errors when trying "git clone git@github.com:frc604/FRC-2014.git" [read this][5] 

 6. Go to Netbeans and create a new "Simple Robot Template" project with the
    following settings:

    | Field            | Value                      |
    | ---------------- | -------------------------- |
    | Project Name     | FRC-2014                   |
    | Package          | com._604robotics.robot2014 |
    | Main Robot Class | Robot2014                  |

    If you get strange errors starting up the code after a deploy, it's because
    you messed these settings up!

 7. Close Netbeans.

 8. Switch back over to your console window, and run the following commands to
    merge in the repository contents with your new project:

        rm -r FRC-2014/src
        mv 2014/* FRC-2014
        rm -r 2014

 9. Re-open Netbeans and you should have a fully set-up project.

If you have any questions, talked to [Michael][4].

[1]: http://wpilib.screenstepslive.com/s/3120/m/7885/l/79405-installing-the-java-development-tools
[2]: http://wpilib.screenstepslive.com/s/3120/m/7885/l/79407-configuring-the-netbeans-installation
[3]: http://git-scm.com/
[4]: mailto:mdsmtp@gmail.com
[5]: https://help.github.com/articles/generating-ssh-keys/
