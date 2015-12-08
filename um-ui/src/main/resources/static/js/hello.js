angular.module('hello', [ 'ngRoute' ]).config(function($routeProvider, $httpProvider) {

    $routeProvider.when('/', {
        templateUrl : 'home.html',
        controller : 'home'
    }).when('/login', {
        templateUrl : 'login.html',
        controller : 'navigation'
    }).otherwise('/');

    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

}).controller('navigation',

function($rootScope, $scope, $http, $location, $route, $window) {

    $scope.tab = function(route) {
        return $route.current && route === $route.current.controller;
    };

    var authenticate = function(credentials, callback) {

        var headers = credentials ? {
            // authorization : "Basic " + btoa(credentials.username + ":" + credentials.password)
            authorization : "Basic " + btoa("live-test" + ":" + "bGl2ZS10ZXN0")
        } : {};

        $http.get("http://localhost:8082/um-webapp/oauth/token?grant_type=password&client_id=live-test&username=" + credentials.username + "&password=" + credentials.password, {
            headers : headers
        }).success(function(data) {
            if (data.access_token) {
                $rootScope.authenticated = true;
                $window.sessionStorage.accessToken = data.access_token;
            } else {
                $rootScope.authenticated = false;
                delete $window.sessionStorage.accessToken;
            }
            callback && callback($rootScope.authenticated);
        }).error(function() {
            $rootScope.authenticated = false;
            delete $window.sessionStorage.accessToken;
            callback && callback(false);
        });

    }

    // authenticate();

    $scope.credentials = {};
    $scope.login = function() {
        authenticate($scope.credentials, function(authenticated) {
            if (authenticated) {
                console.log("Login succeeded")
                $location.path("/");
                $scope.error = false;
                $rootScope.authenticated = true;
            } else {
                console.log("Login failed")
                $location.path("/login");
                $scope.error = true;
                $rootScope.authenticated = false;
            }
        })
    };

    $scope.logout = function() {
        $http.post('logout', {}).success(function() {
            $rootScope.authenticated = false;
            $location.path("/");
        }).error(function(data) {
            console.log("Logout failed")
            $rootScope.authenticated = false;
        });
    }

}).controller('home', function($scope, $http, $window) {
    var headers = {
        "Accept" : "application/json", 
        authorization : "Bearer " + $window.sessionStorage.accessToken
    };
    
    $http.get("http://localhost:8082/um-webapp/api/roles", {
        headers : headers
    }).success(function(data) {
        $scope.greeting = data;
    })
});
