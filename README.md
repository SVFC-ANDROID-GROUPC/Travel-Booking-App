Travel Booking App
==================


PROJECT — GROUP C

The App starts at —

User Authentication System
==========================

1. User Login
==============

Front-End Process
-----------------

**Login Activity** contains:

*   **EditText**: To enter email/username and password.
*   **Button**: To submit login credentials.

**Key Functionality:**

*   Users enter their credentials.
*   App sends an API request to authenticate the user.
*   If successful, the user token is saved locally for session management.

![captionless image](https://miro.medium.com/v2/resize:fit:766/format:webp/1*l5WLaer2c1a4kLSV8FUgaA.png)

2. Forgot Password
===================

Front-End Process
=================

**Forgot Password Activity** contains:

*   **EditText**: To enter the registered email.
*   **Button**: To submit the email to request a password reset.

Key Functionality:
------------------

*   Users input their registered email.
*   App sends an API request to trigger the server to send a password reset email.

![captionless image](https://miro.medium.com/v2/resize:fit:826/format:webp/1*91QaVwWLGPPGYbjG-pJH_Q.png)

3. User Logout
===============

Front-End Process
=================

**Logout Functionality** contains:

*   **Button**: To allow the user to log out.

Key Functionality:
------------------

*   Clears the saved authentication token to invalidate the session.
*   Redirects the user to the Login page.

![captionless image](https://miro.medium.com/v2/resize:fit:820/format:webp/1*F-xxzI-_z5iu1e37aSSIfA.png)

4. Change Password
===================

Front-End Process
=================

**Change Password Activity** contains:

*   **EditText**: For the current password, new password, and confirm password.
*   **Button**: To submit the change.

Key Functionality:
------------------

*   Sends current and new passwords to the server.
*   Updates the password upon successful validation.

![captionless image](https://miro.medium.com/v2/resize:fit:820/format:webp/1*OrtVzbEhIz9nES3_sATC8w.png)

5. User Signup
===============

Front-End Process
=================

**Signup Activity** contains:

*   **EditText**: For inputting name, username, email, and password.
*   **Button**: To submit the registration details.

Key Functionality:
------------------

1.  User fills out the form fields (name, username, email, password).
2.  App validates input fields (ensures no field is empty and email format is correct).
3.  App sends the registration details to the server.
4.  If successful, the user is notified and redirected to the login page.

![captionless image](https://miro.medium.com/v2/resize:fit:770/format:webp/1*2ErO5SQ3jPTu8x2z6Mn4Xg.png)

Main Activity
=============

Description
===========

The **MainActivity** is the primary activity that manages the **Bottom Navigation View** for seamless navigation between fragments. Each fragment serves a distinct purpose:

*   **HomeFragment**: Displays and search destination options and direct can booking.
*   **BookingsFragment**: Shows the user’s upcoming bookings.
*   **ProfileFragment**: Displays and manages user information, including profile details and account settings.

![captionless image](https://miro.medium.com/v2/resize:fit:662/format:webp/1*qIAxNWnTWbjDxHrnJ0otSg.png)![captionless image](https://miro.medium.com/v2/resize:fit:674/format:webp/1*YoplkHhOYLgm0_2i1GSN5Q.png)![captionless image](https://miro.medium.com/v2/resize:fit:666/format:webp/1*AKkLZB3sqdw_rNiQP6KnzQ.png)

![captionless image](https://miro.medium.com/v2/resize:fit:810/format:webp/1*BkFmcZNwmnpLV3mxyAiLBw.png)

Destination Booking Card at Home:

![captionless image](https://miro.medium.com/v2/resize:fit:804/format:webp/1*Ec-lEQAjaBN6qNRT0bPxbg.png)

Upcoming Booking Card at Manage Booking:

![captionless image](https://miro.medium.com/v2/resize:fit:784/format:webp/1*FRNGCJNVDzxn7p95xGLX7Q.png)

Activity Name: `CreateBookingNow`
=================================

**Purpose**:
Facilitates the booking process by:

1.  Displaying details of the selected destination. (Using logo image as default image)
2.  Allowing the user to select a date, number of people, and view updated prices.
3.  Submitting the booking details via an API call.

![captionless image](https://miro.medium.com/v2/resize:fit:762/format:webp/1*M7k1OUtPyaiKw8l7QY31xQ.png)

[https://github.com/SVFC-ANDROID-GROUPC/Travel-Booking-App.git](https://github.com/SVFC-ANDROID-GROUPC/Travel-Booking-App.git)
