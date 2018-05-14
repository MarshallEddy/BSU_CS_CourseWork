<?php
session_start();

if($_SESSION['loggedIn'] == true) { 
    // include_once("logout.php");
} else {
    include_once("login.php");
}
?>

<?php if ((isset($_SESSION['fromMyAcc']) && $_SESSION['fromMyAcc']) || (isset($_SESSION['invalidLogin']) && $_SESSION['invalidLogin'])) { ?>
    <script>
        document.getElementById('loginButton').style.display='block';
    </script>
<?php
    unset($_SESSION['invalidLogin']);
    unset($_SESSION['fromMyAcc']);
} else {
    // do nothing...load normally (not a redirect)
}

$_SESSION['currentPage'] = "index.php";

$_SESSION['gettingComment'] = false;
unset($_SESSION['gettingComment']);

function parseBlogs() {
    $blogTitles = unserialize($_COOKIE['blogTitles']);
    $blogPosts = unserialize($_COOKIE['blogPosts']);
}
?>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Marshall's Blog</title>

    <meta name="description" content="This blog is for CS498: Seminar">
    <meta name="author" content="Marshall Eddy">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"> -->
    <link rel="shortcut icon" href="img/favicon-BSU.ico">
    <link rel="apple-touch-icon" href="img/apple-touch-icon-114x114_BSU.png">
    <link rel="stylesheet" href="www/css/myBlog.css">
    <link rel="stylesheet" href="www/css/loginModal.css">

    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">

    <script type="text/javascript" src="www/JavaScript/jquery/dist/jquery.js"></script>
    <script type="text/javascript" src="www/JavaScript/jquery-validation/dist/jquery.validate.js"></script>
</head>

<?php if($_SESSION['gotBlogs'] == true) {?>
                <body>
            <?php } else { ?>
                <body onload="document.getElementById('blogs').submit();">
            <?php } ?>
    <form class="blogs" id="blogs" action="getBlog-handler.php" style="display:none;" method="POST"></form>
    <div class="container index">
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
        <div class="jumbotron">
            <header>
                <div>
                    <h1>
                        <img src="img/Logo.png" alt="Meddy's Blog Logo" align="left">Welcome to Marshall's Blog</img>
                    </h1>
                </div>
                <p>Check out my CS498 Blog post!</p>
                <nav class="navigation blog-nav">
                    <ul>
                        <li>
                            <a id="index.php" href="index.php">Home</a>
                        </li>
                        <li>
                            <a id="contact.php" href="contact.php">Contact</a>
                        </li>
                    </ul>
                    <ul>
                        <li>
                            <a id="myAccount.php" href="myAccount.php">Account</a>
                        </li>
                        <li>
                            <a id="blogs.php" href="blogs.php">Blogs</a>
                        </li>
                    </ul>
                </nav>
            </div>
        <div class="row">
            <?php 
            $blogTitles = unserialize($_COOKIE['blogTitles']);
            $blogPosts = unserialize($_COOKIE['blogPosts']);

            $titles = array();
            foreach ($blogTitles as $titleArray) {
                foreach ($titleArray as $title) {
                        $titles[] = $title; 
                }
            }
            
            $posts = array();
            foreach ($blogPosts as $postArray) {
                foreach ($postArray as $post) {
                    $posts[] = $post;
                }
            }

            $i = 1;
            $blogs = array_combine($titles, $posts);
            foreach ($blogs as $key => $value) {?>
                <div class="col-md-4"> 
                    <h3>
                        <a href="<?='blogs.php#blog' . $i?>"><?php echo "$key"; ?></a>
                    </h3>
                        <p><?php echo "$value"; ?></P> 
                    </div>
            <?php 
                $i = $i + 1;
                } ?>
        </div>
    <div>
        <footer id="footer">
            <p>©MEddy</p>
        </footer>
    </div>
</div>

<?php
if (isset($_SESSION['currentPage'])) { ?>
    <script>
        document.getElementById('<?=$_SESSION['currentPage']?>').style.color='purple';
    </script>
<?php
}
?>

</body>
</html>
