<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Elitewear - Cart</title>
  <link rel="stylesheet" href="css/main.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
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

<!-- Cart Items -->
<div class="small-container cart-page">
  <table>
    <tr>
      <th>Product</th>
      <th>Quantity</th>
      <th>Subtotal</th>
    </tr>

    <tr>
      <td>
        <div class="cart-info">
          <img src="images/product-3.jpg" width="130px" alt="Pants">
          <div>
            <p>Pants</p>
            <small>Price: Rs 500</small><br>
            <a href="#">Remove</a>
          </div>
        </div>
      </td>
      <td><input type="number" value="1"></td>
      <td>Rs 500</td>
    </tr>

    <tr>
      <td>
        <div class="cart-info">
          <img src="images/product-5.jpg" width="130px" alt="Shocks">
          <div>
            <p>Shocks</p>
            <small>Price: Rs 200</small><br>
            <a href="#">Remove</a>
          </div>
        </div>
      </td>
      <td><input type="number" value="1"></td>
      <td>Rs 200</td>
    </tr>

    <tr>
      <td>
        <div class="cart-info">
          <img src="images/product-2.jpg" width="130px" alt="Shoes">
          <div>
            <p>Shoes</p>
            <small>Price: Rs 800</small><br>
            <a href="#">Remove</a>
          </div>
        </div>
      </td>
      <td><input type="number" value="1"></td>
      <td>Rs 800</td>
    </tr>
  </table>

  <!-- Total Price -->
  <div class="total-price">
    <table>
      <tr>
        <td>Subtotal</td>
        <td>Rs 500</td>
      </tr>
      <tr>
        <td>Subtotal</td>
        <td>Rs 200</td>
      </tr>
      <tr>
        <td>Subtotal</td>
        <td>Rs 800</td>
      </tr>
    </table>
  </div>

  <!-- Checkout Button -->
  <div style="text-align: right; margin-top: 20px;">
    <a href="checkout.jsp" class="checkout-btn">Proceed to Checkout</a>
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
