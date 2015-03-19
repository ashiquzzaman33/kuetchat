<?php
  	include 'db_connect.php';
	$username = $_POST['username'];
	$password = $_POST['password'];
	$email = $_POST['email'];
	$full_name=$_POST['full_name'];
	if($username==null || $password==null || $email==null || $full_name==null)
	{

		header("Location: ../register.php?empty=1");
		exit;		
	}
	$sql="SELECT * FROM userinfo WHERE username='$username' OR email='$email'";

	$result=mysql_query($sql);

	if(mysql_num_rows($result))
	{
		header("Location: ../register.php?dup=1");
		exit;
	}
	else
	{
		$sql="INSERT INTO userinfo (username, fullname, email, usertype) VALUES ('$username', '$full_name','$email', 1)";
		mysql_query($sql);
		$sql="INSERT INTO logininfo (username, password) VALUES ('$username', '$password')";
		mysql_query($sql);
		header("Location: ../register.php?ok=1");
		
	}
?>