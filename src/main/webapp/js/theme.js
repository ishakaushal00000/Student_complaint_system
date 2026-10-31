(function () {
    const savedTheme = localStorage.getItem("websiteTheme") || "dark";

    document.documentElement.setAttribute("data-theme", savedTheme);

    function addThemeButton() {
        if (document.querySelector(".theme-toggle")) return;

        const button = document.createElement("button");
        button.className = "theme-toggle";
        button.type = "button";

        function updateButton() {
            const currentTheme = document.documentElement.getAttribute("data-theme");

            button.textContent = currentTheme === "dark"
                ? "☀️ Light Mode"
                : "🌙 Dark Mode";

            button.setAttribute(
                "aria-label",
                currentTheme === "dark" ? "Switch to light mode" : "Switch to dark mode"
            );
        }

        button.addEventListener("click", function () {
            const currentTheme = document.documentElement.getAttribute("data-theme");
            const newTheme = currentTheme === "dark" ? "light" : "dark";

            document.documentElement.setAttribute("data-theme", newTheme);
            localStorage.setItem("websiteTheme", newTheme);

            updateButton();
        });

        document.body.appendChild(button);
        updateButton();
    }

    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", addThemeButton);
    } else {
        addThemeButton();
    }
})();