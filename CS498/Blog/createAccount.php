<?php
session_start();

function displayErr($err) {
    if (isset($_SESSION['errors'][$err])) { ?>
      <label id="<?= $err . "Error" ?>" class="error" for="<?= $err . "l"?>"> <?= $_SESSION['errors'][$err] ?> </label>
    <?php 
    } 
}
  
if($_SESSION['loggedIn'] == true) { 
    // include_once("logout.php");
} else {
    include_once("login.php");
}

$_SESSION['currentPage'] = "createAccount.php";

if (isset($_SESSION['invalidLogin']) && $_SESSION['invalidLogin']) { ?>
    <script>
        document.getElementById('loginButton').style.display='block';
    </script>
<?php
    unset($_SESSION['invalidLogin']);
    unset($_SESSION['fromMyAcc']);
} else {
    // do nothing...load normally (not a redirect)
}

?>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

        <title>Create Account</title>

        <meta name="description" content="This blog is for CS498: Seminar">
        <meta name="author" content="Marshall Eddy">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link rel="shortcut icon" href="img/favicon-BSU.ico">
        <link rel="apple-touch-icon" href="img/apple-touch-icon-114x114_BSU.png">
        <link rel="stylesheet" href="www/css/myBlog.css">
        <link rel="stylesheet" href="www/css/loginModal.css">
        <link rel="stylesheet" href="www/css/validation.css">

        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">

        <script type="text/javascript" src="www/JavaScript/jquery/dist/jquery.js"></script>
        <script type="text/javascript" src="www/JavaScript/jquery-validation/dist/jquery.validate.js"></script>
        <script type="text/javascript" src="www/JavaScript/createAcc-form-validation.js"></script>
    </head>


    <body id="body">
        <div id="wrapper">
        <?php if($_SESSION['loggedIn'] == true) {?>
                <div class="loginButton">
                <form class="logout" action="logout-handler.php"method="POST">
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
                    <img src="img/myAccountDefault.png" alt="Profile Picture" class="profilePicture">Create Account
                </h1>
            </header>
            <div id="content" class="clearfix">
            <?php include_once("menu.php");?>  
                <section id="right" class="right-author" style="margin-bottom:10%;">
                        <div class="createAccount">
                            <form class="createAccount" id="createAccount" action="registration-handler.php" method="POST" autocomplete="off">

                                <label id="unameLabel" for="uname"><b>Username</b></label>
                                <input type="text" id="uname" placeholder="Enter a Username" name="uname" value="<?php echo $_COOKIE['uname']; ?>" 
                                pattern="^[a-zA-Z][a-zA-Z0-9-_\.]{8,20}$">

                                <br>
                                <label id="pswLabel" for="psw"><b>Password</b></label>
                                <input type="password" id="psw" placeholder="Enter a Password" name="psw" 
                                pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$">
                       
                                <br>
                                <label id="psw_matchLabel" for="psw_match"><b>Password Check</b></label>
                                <input type="password" id="psw_match"placeholder="Re-Enter your Password" name="psw_match">
                                
                                <br>
                                <label id="emailLabel" for="email"><b>E-Mail</b></label>
                                <input type="email" id="email" placeholder="Enter a Valid Email Address" name="email" value="<?php echo $_COOKIE['email']; ?>">
                                <br>
                                <?php displayErr('email'); ?>
                                <br>
                                <button id="createAccButton" class="submit" type="submit">Create Account</button>
                            </form>
                        </div>    
                    </section>   
            </div>    
            <footer>
                <p>©MEddy</p>
            </footer>
        </div>
    </body>    
</html>