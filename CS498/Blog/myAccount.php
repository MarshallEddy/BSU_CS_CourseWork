<?php
session_start();



if($_SESSION['loggedIn'] != true) {
    $_SESSION['fromMyAcc'] = true;
    header("Location: " . $_SESSION['currentPage']);?>
<?php    
} else {
    $_SESSION['currentPage'] = "myAccount.php";
    if($_SESSION['loggedIn'] == true) { 
        // include_once("logout.php");
    } else {
        include_once("login.php");
    }
}

$_SESSION['gettingComment'] = false;
unset($_SESSION['gettingComment']);

if (isset($_SESSION['invalidLogin']) && $_SESSION['invalidLogin']) { ?>
    <script>
        document.getElementById('loginButton').style.display='block';
    </script>
<?php
    unset($_SESSION['invalidLogin']);
} else {
    // do nothing...load normally (not a redirect)
}

?>

<!DOCTYPE>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

        <title>My Account</title>

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
                    <img src="img/myAccountDefault.png" alt="Profile Picture" class="profilePicture">My Account
                </h1>
            </header>
            <div id="content" class="clearfix">
            <?php include_once("menu.php");
            if (isset($_SESSION['currentPage'])) { ?>
                <script>
                    document.getElementById('<?=$_SESSION['currentPage']?>').style.color='purple';
                </script>
            <?php
            }
            ?>    
                <section id="right" class="right-author" style="margin-bottom:10%;">
                    <div class="myAccountSettings">
                        <p> Welcome <?php print htmlspecialchars($_COOKIE['unamel']); ?>!</p>
                        <br>
                        <p> Your Email address is: <?php print htmlspecialchars($_COOKIE['email']); ?>. </p> 
                    </div>    
                </section>
            </div>    
            <footer>
                <p>©MEddy</p>
            </footer>
        </div>
    </body>    
</html>