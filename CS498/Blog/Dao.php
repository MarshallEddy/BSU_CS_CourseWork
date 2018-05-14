<?php
class Dao {
    private $host = "us-cdbr-iron-east-05.cleardb.net";
    private $db = "heroku_3c1d15a3a043353";
    private $user = "b893d025eac225";
    private $pass = "62593741";

    public function getConnection () {
        return
            new PDO("mysql:host={$this->host};dbname={$this->db}", $this->user, $this->pass);
    }

    public function getConnectionStatus() {
        $conn = $this->getConnection();
        return $conn->getAttribute(constant("PDO::ATTR_CONNECTION_STATUS"));
    }

    public function saveComment($blog, $uname, $comment) {
        $conn = $this->getConnection();
        $mysql_date_now = date("Y-m-d H:i:s");
        $_SESSION['dateTime'] = $mysql_date_now;
        $saveQuery = 
        "INSERT INTO comment
        (blog, uname, comment, Date_Time)
        VALUES
        (:blog, :uname, :comment, :Date_Time)";
        $q = $conn->prepare($saveQuery);
        $q->bindParam(":blog", $blog);
        $q->bindParam(":uname", $uname);
        $q->bindParam(":comment", $comment);
        $q->bindParam(":Date_Time", $mysql_date_now);
        try {
            $q->execute();
        } catch (PDOException $e) {
            echo $e->getMessage();
            return false;
        }
    }

    public function getComments($blog) {
        $conn = $this->getConnection();
        $q = $conn->prepare("SELECT uname, comment, Date_Time FROM comment WHERE blog=:blog");
        $q->bindParam(':blog', $blog);
        $q->setFetchMode(PDO::FETCH_ASSOC);
        $q->execute();
        $results = $q->fetchAll();
        return $results;
    }

    public function getUname($blog) {
        $conn = $this->getConnection();
        $q = $conn->prepare("SELECT uname FROM comment WHERE blog=:blog");
        $q->bindParam(':blog', $blog);
        $q->setFetchMode(PDO::FETCH_ASSOC);
        $q->execute();
        $results = $q->fetchAll();
        return $results;
    }

    public function getComment($blog) {
        $conn = $this->getConnection();
        $q = $conn->prepare("SELECT comment FROM comment WHERE blog=:blog");
        $q->bindParam(':blog', $blog);
        $q->setFetchMode(PDO::FETCH_ASSOC);
        $q->execute();
        $results = $q->fetchAll();
        return $results;
    }

    public function getDateTime($blog) {
        $conn = $this->getConnection();
        $q = $conn->prepare("SELECT Date_Time FROM comment WHERE blog=:blog");
        $q->bindParam(':blog', $blog);
        $q->setFetchMode(PDO::FETCH_ASSOC);
        $q->execute();
        $results = $q->fetchAll();
        return $results;
    }

    public function addUser($uname, $psw, $email) {
        $conn = $this->getConnection();

        // php function password_hash() auto-generates salt when no salt is specified.
        $digest = password_hash($psw, PASSWORD_DEFAULT);
        if (!$digest) {
            throw new Exception("Password was not hashed successfully.");
        }

        $saveQuery = 
        "INSERT INTO users
        (uname, psw, email)
        VALUES
        (:uname, :psw, :email)";
        $q = $conn->prepare($saveQuery);
        $q->bindParam(":uname", $uname);
        $q->bindParam(":psw", $digest);
        $q->bindParam(":email", $email);
        try {
            $q->execute();
        } catch (PDOException $e) {
            echo $e->getMessage();
            return false;
        }
    }

    public function getBlogPosts() {
        $conn = $this->getConnection();
        $query = $conn->prepare("SELECT blog_post FROM blog_posts");
        $query->setFetchMode(PDO::FETCH_ASSOC);
        $query->execute();
        $results = $query->fetchAll();
        return $results;
    }

    public function getBlogTitles() {
        $conn = $this->getConnection();
        $query = $conn->prepare("SELECT blogTitle FROM blog_posts");
        $query->setFetchMode(PDO::FETCH_ASSOC);
        $query->execute();
        $results = $query->fetchAll();
        return $results;
    }


    protected function getUsersByKey($key, $val) {
        $query = $this->conn->prepare("SELECT * FROM users WHERE $key = :val");
        $query->bindParam(':val', $val);
        return $this->executeQuery($query);
    }

    public function userExists($uname) {
        $conn = $this->getConnection();
        $query = "SELECT uname FROM users WHERE uname = :uname";
        $q = $conn->prepare($query);
        $q->bindParam(':uname', $uname);
        $q->execute();
        return ($q->rowCount() > 0);
    }

    public function loginUser($uname, $psw) {
		$conn = $this->getConnection();
		$query = "SELECT psw FROM users WHERE uname = :uname";
		$q = $conn->prepare($query);
		$q->bindParam(':uname', $uname);
		$q->execute();
		$row = $q->fetch();
        $digest = $row['psw'];
        // setcookie("ps", $row);

        $query2 = "SELECT email FROM users WHERE uname = :uname";
        $q2 = $conn->prepare($query2);
        $q2->bindParam(':uname', $uname);
        $q2->execute();
        $row2 = $q2->fetch();
        $digest2 = $row2['email'];
        setcookie("email", $digest2);

		return password_verify($psw, $digest);
    }

}

