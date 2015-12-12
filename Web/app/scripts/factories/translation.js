angular.module('eva').factory('translation', function () {
    var strings = {};
    strings.english = {
        challenge_score_notification: "Difficulty: ",
        accept_challenge: "Accept challenge",
        choose_challenge: "Choose a challenge:",
        welcome_notification: "Welcome ",
        get_challenge: "Look at challenge",
        get_progress: "Look at progress",
        login_alternative_login: "Alternative Login",
        login_facebook_login: "Log in with Facebook",
        login_email: "Email",
        login_password: "Password",
        login_lost_password: "Lost password",
        login_button: "Login",
        login_or: "Or",
        register: "Register",
        register_personal_info_label: "Personal Info",
        register_first_name: "First Name",
        register_name: "Last Name",
        register_email: "E-mail",
        register_address_label: "Address",
        register_street: "Street",
        register_postal_code: "Postal Code",
        register_city: "City",
        register_account_label: "Account",
        register_sex_male: "Male",
        register_sex_female: "Female",
        create_account: "Create account",
        action_bar_search: "Search",
        action_bar_share: "Share",
        challenge: "Challenge",
        current_challenge: "Current challenge",
        logout: "Logout",
        grade_omnivore: "Omnivore",
        grade_pescetariar: "Pescetariar (no meat, fish ok)",
        grade_parttime_vegetarian: "Parttime vegetarian (at least 3 times per week vegetarian)",
        grade_vegetarian: "Vegetarian (no meat or fish)",
        grade_vegan: "Vegan (no animal products)"
    };
    strings.dutch = {};
    strings.french = {};
    return strings;
});
