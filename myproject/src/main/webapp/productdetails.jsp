<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Elitewear - Product Details</title>
  <link rel="stylesheet" href="css/main.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<!-- Header -->
<div class="header">
  <div class="container">
    <div class="navbar">
      <div class="logo">
        <a href="index.jsp"><img src="images/Elite.logo.png" width="140px" alt="Elitewear Logo"></a>
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

<!-- Product Detail -->
<div class="small-container single-product">
  <div class="row">
    <div class="col-2">
      <img src="images/product-2.jpg" width="100%" alt="Black Shoes">
    </div>
    <div class="col-2">
      <p>Home / Shoes</p>
      <h1>Black Shoes</h1>
      <h4>Rs 800</h4>
      <select>
        <option>Select Size</option>
        <option>XXL</option>
        <option>XL</option>
        <option>Large</option>
        <option>Medium</option>
        <option>Small</option>
      </select>
      <input type="number" value="1">
      <a href="cart.jsp" class="btn">Add to Cart</a>

      <h3>Product Details <i class="fa fa-indent"></i></h3>
      <br>
      <p>
        Step up your style game with these sleek black sneakers from EliteWear. 
        Crafted with high-quality synthetic leather and a breathable mesh lining, these shoes offer a perfect blend of comfort, durability, and modern design. 
        These shoes are your go-to pick for both style and comfort. Available in all standard sizes.
      </p>
    </div>
  </div>
</div>

<!-- Related Products -->
<div class="small-container">
  <div class="row row-2">
    <h2>Related Products</h2>
    <p>View More</p>
  </div>

  <div class="row">
    <div class="col-4">
      <a href="productdetails.jsp"><img src="images/product-13.jpg" alt="Bl-TShirt"></a>
      <a href="productdetails.jsp"><h4>Bl-TShirt</h4></a>
      <div class="rating">
        <i class="fa fa-star"></i><i class="fa fa-star"></i>
        <i class="fa fa-star"></i><i class="fa fa-star"></i>
        <i class="fa fa-star-o"></i>
      </div>
      <p>Rs 500</p>
    </div>

    <div class="col-4">
      <a href="productdetails.jsp"><img src="images/product-11.jpg" alt="Shoes"></a>
      <a href="productdetails.jsp"><h4>Shoes</h4></a>
      <div class="rating">
        <i class="fa fa-star"></i><i class="fa fa-star"></i>
        <i class="fa fa-star"></i><i class="fa fa-star"></i>
        <i class="fa fa-star-o"></i>
      </div>
      <p>Rs 2000</p>
    </div>

    <div class="col-4">
      <a href="productdetails.jsp"><img src="images/product-14.jpg" alt="Black Watch"></a>
      <a href="productdetails.jsp"><h4>Black-Watch</h4></a>
      <div class="rating">
        <i class="fa fa-star"></i><i class="fa fa-star"></i>
        <i class="fa fa-star"></i><i class="fa fa-star"></i>
        <i class="fa fa-star-o"></i>
      </div>
      <p>Rs 700</p>
    </div>

    <div class="col-4">
      <a href="productdetails.jsp"><img src="images/product-15.jpg" alt="Blue TShirt"></a>
      <a href="productdetails.jsp"><h4>Blue-TShirt</h4></a>
      <div class="rating">
        <i class="fa fa-star"></i><i class="fa fa-star"></i>
        <i class="fa fa-star"></i><i class="fa fa-star"></i>
        <i class="fa fa-star-o"></i>
      </div>
      <p>Rs 500</p>
    </div>
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
