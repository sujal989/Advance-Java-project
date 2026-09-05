<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Elitewear - Contact Us</title>
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
        <a href="cart.jsp"><img src="images/cart.png" width="30" height="30" alt="Cart" /></a>
        <img src="images/menu.png" class="menu-icon" onclick="menutoggle()" alt="Menu Toggle" />
      </div>
    </div>
  </div>

  <!-- Contact Form Section -->
  <div class="contact-page small-container">
    <div class="form-wrapper">
      <h2>Contact Us</h2>
      <form id="ContactForm" action="#" method="POST" autocomplete="off">
        <label for="name">Your Name</label>
        <input type="text" id="name" name="name" placeholder="Your Name" required />

        <label for="email">Your Email</label>
        <input type="email" id="email" name="email" placeholder="Your Email" required />

        <label for="phone">Phone Number</label>
        <input type="tel" id="phone" name="phone" placeholder="Phone Number" />

        <label for="message">Your Message</label>
        <textarea id="message" name="message" placeholder="Your Message" rows="5" required></textarea>

        <button type="submit" class="btn">Send Message</button>
      </form>
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
            <img src="images/play-store.png" alt="Play Store" />
            <img src="images/app-store.png" alt="App Store" />
          </div>
        </div>

        <div class="footer-col-2">
          <img src="images/Elitewear-3.png" alt="Elitewear Logo" />
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

</body>
</html>
