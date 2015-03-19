<?php
  include 'inner/header.php';
  include 'inner/season_check.php';
  if($_SESSION['user_type']==1)
  {
    header("Location: index.php");
    die();
  }
  $sql="SELECT * FROM userinfo NATURAL JOIN logininfo";
  $result_set = mysql_query($sql);
  
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
            <th>Full Name</th><th>user name</th><th>E-mail</th><th></th>
          </tr>
<?php
  while ($found_user = mysql_fetch_array($result_set)) 
  {
    $name=$found_user['fullname'];
    $user=$found_user['username'];
    $mail=$found_user['email'];
    $block=$found_user['block'];
?>  
          <tr>
            <td><?php echo $name; ?></td>
            <td><?php echo $user; ?></td>
            <td><?php echo $mail; ?></td>
            <td>
                <form name="input" action="inner/removal.php" method="post">
                  <input type="hidden" value=<?php echo $user;?> name="username" />
                  <input type="submit" value="Remove">
                </form>
            </td>
            <td>
                <form name="input" action="inner/blocking.php" method="post">
                  <input type="hidden" value="<?php echo $user;?>" name="username" />
                  <input type="hidden" value="<?php echo $block;?>" name="block" />
                  <input type="submit" value="<?php if($block==1) echo "unblock"; else echo "block";?>">
                </form>
            </td>
          </tr>
<?php
  }
?>
          
          
          
          
        </table>
        
      <!-- </div> -->
      
      <div class="clr"></div>
    </div>
  </div>

<?php
  include 'inner/footer.php';
?>