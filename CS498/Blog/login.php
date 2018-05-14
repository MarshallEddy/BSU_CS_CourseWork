<?php
session_start();

function displayError($err) {
    if (isset($_SESSION['errors'][$err])) { ?>
      <label id="<?= $err . "Error" ?>" class="error" for="<?= $err . "l"?>"> <?= $_SESSION['errors'][$err] ?> </label>
    <?php 
    } 
}
?>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Marshall's Blog</title>

    <meta name="description" content="This blog is for CS498: Seminar">
    <meta name="author" content="Marshall Eddy">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="shortcut icon" href="img/favicon-BSU.ico">
    <link rel="apple-touch-icon" href="img/apple-touch-icon-114x114_BSU.png">
    <link rel="stylesheet" href="www/css/loginModal.css">
    <link rel="stylesheet" href="www/css/validation.css">

    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">

    <script type="text/javascript" src="www/JavaScript/jquery/dist/jquery.js"></script>
    <script type="text/javascript" src="www/JavaScript/jquery-validation/dist/jquery.validate.js"></script>
    <script type="text/javascript" src="www/JavaScript/login-form-validation.js"></script>

</head>
<body>
    <!-- The Modal -->
<div id="loginButton" class="modal">
        <span onclick="document.getElementById('loginButton').style.display='none'" 
      class="close" title="Close Modal">&times;</span>
      
        <!-- Modal Content -->
        <form class="loginModal animate" name="loginModal" id="login" action="login_handler.php" method="POST">
          <div class="imgcontainer">
            <img src="img/loginAvatar.png" alt="Avatar" class="avatar">
          </div>
      
          <div class="container login">
            <label for="uname"><b>Username</b></label>
            <input type="text" placeholder="Please Enter your username" id="unamel" name="uname" minlength="8" value="<?php echo $_COOKIE['unamel']; ?>">
            <br>

            <?php displayError('uname');?>

            <br>
            <label for="psw"><b>Password </b></label>
            <input type="password" placeholder="Please enter your password" id="pswl" name="psw">
            <br>

            <?php displayError('psw');?>

            <button class="submit" type="submit">Login</button>
            <br>
            
            <button type="submit"
            onclick="location.href='createAccount.php'">Sign Up</button>
            <br>
            <!-- <label>
              <input type="checkbox" checked="checked" name="remember"> Remember me
            </label> -->
          </div>
      
          <div class="container" style="background-color:#f1f1f1; width:95%;">
            <button type="button" onclick="document.getElementById('loginButton').style.display='none'" class="cancelbtn">Cancel</button>
            <!-- <span class="psw">Forgot <a href="#">password?</a></span> -->
          </div>
        </form>
      </div>
<script src="www/JavaScript/get-modal.js"></script>
</body>
</html>
<?php //clearError();?>