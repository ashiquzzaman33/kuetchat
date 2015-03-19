<?php
  include 'inner/header.php';
?>
          <h2><span>Welcome to KuetChat</span></h2>
          <p>Welcome to the beta version of KuetChat, an online messenger. Connect with your friends and others share
            your files. As this is a Beta version of our messenger, the audio and video chat option is temporarily
            unavailable. </p>
        </div>
      </div>
      <div class="sidebar">
        <div class="gadget">

<?php
  if(!isset($_SESSION['user_name'])) 
  {
?>
          
          <h2 class="star"><span>Login</span> Here</h2>
          <form action = "inner/login_process.php" method = "post">
          <table>
          <tr><td>
            </td>
          </tr>
          <tr>
            <td>Username: </td> <td><input type="text" name="username" /></td><br />
          </tr>
          <tr>
            <td>Password: </td><td><input type="password" name="password" /></td><br />
          </tr>
          <tr><td></td><td><input type = "submit" name="submit" value="login" /></td></tr>
          </table>
          </form>
          <?php
              if(isset($_GET['error']) && $_GET['error']=='yes')
                echo '<p style="color:red">invalid username or password</p>';
            ?>
<?php
  }
?>

        </div>
        
      </div>
      <div class="clr"></div>
    </div>
  </div>
  

<?php
  include 'inner/footer.php';
?>