<?php
  include 'inner/header.php';
  include 'inner/season_check.php';
?>
<?php
  $username=$_SESSION['user_name'];
  $full_name="";
  $address="";
  $email="";
  $sql="SELECT * FROM userinfo WHERE username='$username'";
  $result_set = mysql_query($sql);
  if (mysql_num_rows($result_set) == 1) 
  {
    $found_user = mysql_fetch_array($result_set);
    $full_name=$found_user['fullname'];
    $address=$found_user['address'];
    $email=$found_user['email'];
  }
?>
        </div>
      </div>
    
      <div class="clr"></div>
    </div>
  </div>
  <div class="fbg">
    <div class="fbg_resize">
      <!-- <div class="col c2"> -->
        <h2>Profile- <?php echo $_SESSION['user_name']; ?>:</h2>
        <table>
          <tr>
            <td><h3>Full name:</h3></td><td><?php echo $full_name; ?></td>
          </tr>
          <tr> <td><b>user name:</b></td><td><?php echo $_SESSION['user_name']; ?> </td> </tr>
          <tr> <td><b>E-mail:</b></td> <td><?php echo $email; ?> </td> </tr>
          
          
          
        </table>
        
      <!-- </div> -->
      
      <div class="clr"></div>
    </div>
  </div>

<?php
  include 'inner/footer.php';
?>