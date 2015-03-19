<?php
  if(empty($_SESSION)) // if the session not yet started 
   session_start();
  include 'db_connect.php';
  //include ('functions.php');
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
table {
    width: 50%;
    border-collapse: collapse;
}

table, td, th {
    /*border: 1px solid black;*/
    padding: 10px;
}

th {text-align: justify;}
</style>
<title>KuetChat-An online messenger</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/cufon-yui.js"></script>
<script type="text/javascript" src="js/arial.js"></script>
<script type="text/javascript" src="js/cuf_run.js"></script>
<script src="inner/scripts.js" type="text/javascript"></script>
</head>
<body>
<div class="main">
  <div class="header">
    <div class="header_resize">
      <div class="menu_nav">
        <ul>
          <li class="active"><a href="index.php">Home</a></li>
          <li><a href="about.php">About Us</a></li>
          <li><a href="contact.php">Contact Us</a></li>
<?php
  if(isset($_SESSION['user_name']))
  {
     if($_SESSION['user_type']==1)
     {
?>
          <li><a href="profile.php">My Profile - <?php echo $_SESSION['user_name']; ?> </a></li>
<?php
    }
    else
    {
?>
          <li><a href="admin_panel.php">Admin Panel </a></li>
<?php
    }
?>
          <li><a href="inner/logout.php">Logout</a></li>
<?php
  }
  else{
?>
          <li><a href="register.php">Register</a></li>
<?php
}
?>
        </ul>
        <div class="clr"></div>
      </div>
      <div class="clr"></div>
      <div class="logo">
        <h1><a href="index.html">KuetChat<br />
          <small>An Online Messenger</small></a></h1>
      </div>
    </div>
  </div>
  <div class="content">
    <div class="content_resize">
      <div class="mainbar">
        <div class="article">