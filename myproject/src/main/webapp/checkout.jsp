<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Elitewear - Checkout</title>

  <link rel="stylesheet" href="css/main.css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>

<!-- Header -->
<div class="header">
  <div class="container">
    <div class="navbar">
      <div class="logo">
        <a href="index.jsp"><img src="images/Elite.logo.png" width="140px" alt="Elitewear Logo" /></a>
      </div>
      <nav>
        <ul id="MenuItems">
          <li><a href="index.jsp">Home</a></li>
          <li><a href="product.jsp">Products</a></li>
          <li><a href="aboutus.jsp">About</a></li>
          <li><a href="contactus.jsp">Contact</a></li>
          <li><a href="account.jsp">Account</a></li>
        </ul>
      </nav>
      <a href="cart.jsp"><img src="images/cart.png" width="30px" height="30px" alt="Cart"></a>
      <img src="images/menu.png" class="menu-icon" onclick="menutoggle()" alt="Menu Toggle">
    </div>
  </div>
</div>

<!-- Checkout Section -->
<div class="small-container payment">
  <h2>Payment Gateway</h2>
  <div class="form">

    <div class="card space icon-relative">
      <label for="cardholder">Card holder:</label>
      <input type="text" id="cardholder" class="input" placeholder="Elite Wear" autocomplete="cc-name" />
      <i class="fas fa-user"></i>
    </div>

    <div class="card space icon-relative">
      <label for="cardnumber">Card number:</label>
      <input type="text" id="cardnumber" class="input" data-mask="0000 0000 0000 0000" placeholder="Card Number" autocomplete="cc-number" />
      <i class="far fa-credit-card"></i>
    </div>

    <div class="card-grp space">
      <div class="card-item icon-relative">
        <label for="expiry">Expiry date:</label>
        <input type="text" id="expiry" class="input" data-mask="00 / 00" placeholder="00 / 00" autocomplete="cc-exp" />
        <i class="far fa-calendar-alt"></i>
      </div>

      <div class="card-item icon-relative">
        <label for="cvc">CVC:</label>
        <input type="text" id="cvc" class="input" data-mask="000" placeholder="000" autocomplete="cc-csc" />
        <i class="fas fa-lock"></i>
      </div>
    </div>

    <div class="btn" role="button" tabindex="0">Pay</div>
  </div>
</div>

<!-- Footer -->
<div class="footer">
  <div class="container">
    <div class="row">
      <div class="footer-col-1">
        <h3>Download Our App</h3>
        <p>Download the app for Android and iOS mobile phones.</p>
        <div class="app-logo">
          <img src="images/play-store.png" alt="Play Store">
          <img src="images/app-store.png" alt="App Store">
        </div>
      </div>

      <div class="footer-col-2">
        <img src="images/Elitewear-3.png" alt="Elitewear Logo">
      </div>

      <div class="footer-col-3">
        <h3>Useful Links</h3>
        <ul>
          <li>Coupons</li>
          <li>Blog Post</li>
          <li>Return Policy</li>
          <li>Join Affiliate</li>
        </ul>
      </div>

      <div class="footer-col-4">
        <h3>Follow Us</h3>
        <ul>
          <li>Facebook</li>
          <li>Twitter</li>
          <li>Instagram</li>
          <li>YouTube</li>
        </ul>
      </div>
    </div>
    <hr />
    <p class="Copyright">Copyright 2025 - EliteWear</p>
  </div>
</div>

<!-- JS for toggle menu -->
<script>
  var MenuItems = document.getElementById("MenuItems");
  MenuItems.style.maxHeight = "0px";
  function menutoggle() {
    if (MenuItems.style.maxHeight === "0px") {
      MenuItems.style.maxHeight = "200px";
    } else {
      MenuItems.style.maxHeight = "0px";
    }
  }
</script>

<!-- jQuery & Mask plugin for input masks -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>
<script>
  $(document).ready(function () {
    $("[data-mask]").each(function () {
      var mask = $(this).attr("data-mask");
      $(this).mask(mask);
    });

    $(".btn").click(function () {
      alert("Payment processing is not implemented yet.");
    });
  });
</script>

</body>
</html>
