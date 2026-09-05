<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Elitewear - About Us</title>
  <link rel="stylesheet" href="css/main.css">  
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

  <!-- Header -->
  <div class="header">
    <div class="container">
      <div class="navbar">
        <div class="logo">
          <a href="index.jsp"><img src="images/Elite.logo.png" width="140px" alt="logo"></a>
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
        <img src="images/menu.png" class="menu-icon" alt="Menu" onclick="menutoggle()">
      </div>
    </div>
  </div>

  <!-- About Section -->
  <div class="about-section small-container">
    <h2>About Elite Wear</h2>
    <p>
      At Elite Wear, we believe that fashion is more than just clothing — it’s a statement of confidence, individuality, and style. Founded with the vision to empower every wearer, Elite Wear brings you high-quality, trendsetting apparel that blends comfort with elegance.
    </p>
    <p>
      Our collection is designed for those who strive to stand out, whether it’s in everyday casuals or sophisticated formal wear. We carefully select fabrics and pay attention to every detail, ensuring that each piece not only looks great but feels amazing too.
    </p>
    <p>
      Committed to excellence, innovation, and sustainability, Elite Wear is your go-to brand for fashion that lasts and speaks volumes. Join the Elite Wear family and elevate your wardrobe to the next level.
    </p>
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
      <hr>
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

</body>
</html>
