# ChappStick

Android app to ''chat'' with apps through custom server (ServerChapp).

### Activities

7 screens:

- LoginActivity - Log in with username and password.
- SignupActivity - Sign up and create account.
- HomescreenActivity - presents app icons in list view. A click will open a chat with that app.
- MessagingActivity - Starts chat with app. App name and welcome message are loaded with activity. Welcome message will generally give user directions for use.
- AddAppsActivity - Select multiple apps and save to homescreen.
- DeleteAppsActivity - Select multiple apps to delete from homescreen.
- SettingsActivity - Modify account information.

### Server

Heroku server written in Node JS. All information concerning apps are accessed 
