<?php
session_start();
if($_SESSION['loggedIn'] == true) { 
    // include_once("logout.php");
} else {
    include_once("login.php");
}


if ((isset($_SESSION['fromMyAcc']) && $_SESSION['fromMyAcc']) || (isset($_SESSION['invalidLogin']) && $_SESSION['invalidLogin'])) { ?>
    <script>
        document.getElementById('loginButton').style.display='block';
    </script>
<?php
    unset($_SESSION['invalidLogin']);
    unset($_SESSION['fromMyAcc']);
} else {
    // do nothing...load normally (not a redirect)
}

$_SESSION['currentPage'] = "contact.php";

$_SESSION['gettingComment'] = false;
unset($_SESSION['gettingComment']);

?>

<!DOCTYPE>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

        <title>Contact</title>

        <meta name="description" content="This blog is for CS498: Seminar">
        <meta name="author" content="Marshall Eddy">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link rel="shortcut icon" href="img/favicon-BSU.ico">
        <link rel="apple-touch-icon" href="img/apple-touch-icon-114x114_BSU.png">
        <link rel="stylesheet" href="www/css/myBlog.css">
        <link rel="stylesheet" href="www/css/loginModal.css">

        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">

        <script type="text/javascript" src="www/JavaScript/jquery/dist/jquery.js"></script>
        <script type="text/javascript" src="www/JavaScript/jquery-validation/dist/jquery.validate.js"></script>
    </head>

    <body id="body">
        <div id="wrapper">
        <?php if($_SESSION['loggedIn'] == true) {?>
                <div class="loginButton">
                <form class="logout" action="logout-handler.php" method="POST">
                    <button type="submit">Logout</button>
                </form>
                </div>
            <?php } else { ?>
                    <div class="loginButton">
                        <button onclick="document.getElementById('loginButton').style.display='block'">Login</button>
                    </div>
            <?php } ?>
            <header id="header">
                <h1 class="contactHeader">
                    Marshall's Contact Information
                </h1>
            </header>
            <div id="content" class="clearfix">
            <?php include_once("menu.php");?>  
            <?php
                if (isset($_SESSION['currentPage'])) { ?>
                <script>
                    document.getElementById('<?=$_SESSION['currentPage']?>').style.color='purple';
                </script>
            <?php
                }
            ?>    
                <section id="right" class="right-author" style="margin-bottom:10%;">
                    <p>
                        <h2>About the Author</h2>
                        <p class="blogParagraph">
                            My name is Marshall Eddy. I'm currently a Senior at Boise State
                            University as a Computer Science major with a cyber security emphasis.
                            I will  be graduating in the Spring of 2018. I'm currently employed
                            part-time at US Electrical Services Inc. in their E-Commerce department
                            as the sole mobile app developer. The mobile app uses Apache Cordova/Phonegap
                            using Framework7 as the framework for the Cordova Application. 
                            <br />
                            <br />
                            I'm originally from Franklin, Tennessee: born and raised. I came to Boise State 
                            University to pursue my love of Snowboarding! And I guess to get an education
                            or something like that. 
                        </p>
                        <p   class="right-sidebar-contact">
                            <h2>Contact</h2>
                            <li> Marshall Eddy</li>
                            <li> Student ID: 114017540</li>
                            <li> BSU email: marshalleddy@u.boisestate.edu</li>
                            <li> Work email: meddy@usesi.com</li>
                            <li> Personal email: marshalleddy@me.com</li>
                        </p>    
                    </p>
                </section>
            </div>    
            <footer>
                <p>©MEddy</p>
            </footer>
        </div>
    </body>    
</html>