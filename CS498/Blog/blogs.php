<?php
session_start();
if($_SESSION['loggedIn'] == true) { 
    // include_once("logout.php");
} else {
    include_once("login.php");
}

$_SESSION['currentPage'] = "blogs.php";

if ((isset($_SESSION['fromMyAcc']) && $_SESSION['fromMyAcc']) || (isset($_SESSION['invalidLogin']) && $_SESSION['invalidLogin'])) { ?>
    <script>
        document.getElementById('loginButton').style.display='block';
    </script>
<?php
    unset($_SESSION['invalidLogin']);
    unset($_SESSION['fromMyAcc']);

    if (strpos($_SERVER['REQUEST_URI'], '#blog')) {
        $redirectBlog = substr($_SERVER['REQUEST_URI'], -1);
        $_SESSION['indexRedirect'][$redirectBlog];
    }

} else {
    // do nothing...load normally (not a redirect)
}

?>

<!DOCTYPE>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

        <title>Blogs</title>

        <meta name="description" content="This blog is for CS498: Seminar">
        <meta name="author" content="Marshall Eddy">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link rel="shortcut icon" href="img/favicon-BSU.ico">
        <link rel="apple-touch-icon" href="img/apple-touch-icon-114x114_BSU.png">
        <link rel="stylesheet" href="www/css/myBlog.css">
        <link rel="stylesheet" href="www/css/loginModal.css">
        <link rel="stylesheet" href="www/css/comments.css">

        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">

        <script type="text/javascript" src="www/JavaScript/jquery/dist/jquery.js"></script>
        <script type="text/javascript" src="www/JavaScript/jquery-validation/dist/jquery.validate.js"></script>
    </head>

<?php if($_SESSION['gettingComment'] == true) {?>
        <body id="body">
<?php } else { ?>
        <body id="body" onload="document.getElementById('comments').submit();">
<?php } ?>
<form class="comments" id="comments" action="getComment-handler.php" style="display:none;" method="POST"></form>
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
                <h1 class="contactHeader blogsHeader">
                    Blogs
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

            <script type="text/javascript">
                function toggle_visibility(c) {
                    var e = document.getElementsByClassName(c);
                    var i;
                    for (i = 0; i < e.length; i++) {
                        if(e[i].style.display == 'block') {
                            e[i].style.display = 'none';
                        }
                        else {
                            e[i].style.display = 'block';
                        }
                    }
                }
            </script>
                <section id="right" class="right-author" style="margin-bottom:10%;">
                    <div>
                        <ul class = "blogList">

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
                            $blog = array();
                            $blogs = array_combine($titles, $posts);
                            $i = 1;
                           
                            foreach ($blogs as $key => $value) {?>
                            <?php $_SESSION['blog'][$i] = $key; ?>
                                <li class="blogList">
                                    <a href" " id="<?='blog' . $i?>"><?php echo "$key"; ?>:</a>
                                   
                                    <div class="blogPostDescription">
                                        <?php echo "$value"; ?>
                                    </div>
                                     <?php 
                                    $commUname = $_SESSION['commUname'][$i];
                                    $comm = $_SESSION['comm'][$i];
                                    $commDateTime = $_SESSION['commDateTime'][$i]; 

                                    ?>
                                    
                                    <a href="#" class="commentHeaders" id="<?='commentHeader' . $i?>" onclick="toggle_visibility('<?="comments" . $i;?>');return false;">Comments: </a>

                                    <?php for ($l = 0; $l < count($comm); $l++) {?>
                                    
                                        <div id="<?= "comments" . $i . "." . $l ?>" class="<?="comments" . $i?>" style="display:none;">
                                            <div class="commNameDateTime commentPage">
                                                <b><?php echo $commUname[$l]["uname"];?></b>
                                                <small class="time"> <?php echo "\t" . $commDateTime[$l]["Date_Time"] . "\n"; ?> </small>
                                            </div>
                                            <div class="comm commentPage">
                                                <p>
                                                    <?php echo $comm[$l]["comment"] . "\n";?>
                                                </p>
                                            </div>
                                        </div>
                                        
                                    <?php
                                    }
                                    if ($_SESSION['commented'][$i] == true) { ?>
                                        <script>
                                            var e = document.getElementsByClassName('<?="comments" . $i;?>');
                                            for (i = 0; i < e.length; i++) {
                                                e[i].style.display = 'block';
                                            }
                                        </script>
                                    <?php } ?>
                                        
                                    <?php if($_SESSION['loggedIn'] != true) { ?> 
                                        <br><br>
                                    <?php } else { ?>    
                                        <form class="commentBox" id="<?="commentBoxForm" . $i?>" action="comment-handler.php" method="POST">
                                            <label id="<?="commentBoxLabel" . $i?>" for="<?="commentBox" . $i?>"></label>
                                            <textarea id="<?="commentBox" . $i?>" rows="2" cols="50" placeholder="Add a Comment..." name="commentBox" form="<?="commentBoxForm" . $i?>"></textarea>
                                            <input id="<?="commentBoxID" . $i?>" name="commentBoxID" value="<?= $i ?>" style="display:none;" form="<?="commentBoxForm" . $i?>"></input>
                                            <button id="<?="addComment" . $i?>" class="submit" type="submit">Comment</button>
                                        </form>
                                    <?php } ?>
                                </li>
                            <?php 
                            $i = $i + 1;
                            }?>
                        </ul>
                    </div>
                </section>
            </div>    
            <footer>
                <p>©MEddy</p>
            </footer>
        </div>
    </body>    
</html>