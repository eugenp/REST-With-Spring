angular.module('hello', [ 'ngResource', 'ngRoute' ]).config(function($routeProvider, $httpProvider) {

    $routeProvider.when('/', {
        templateUrl : 'home.html',
        controller : 'home'
    }).when('/login', {
        templateUrl : 'login.html',
        controller : 'navigation'
    }).otherwise('/');

    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

}).controller('navigation',

function($rootScope, $scope, $http, $httpParamSerializer, $location, $route, $window) {

    $scope.tab = function(route) {
        return $route.current && route === $route.current.controller;
    };

    var authenticate = function(credentials, callback) {
        $scope.data = {grant_type:"password", username: credentials.username, password: credentials.password, client_id: "um"};

        var req = {
            method: 'POST',
            url: "http://localhost:8082/um-webapp/oauth/token",
            headers: {
                "Authorization": "Basic " + btoa("um" + ":" + "VXB0YWtlLUlyb24h"),
                "Content-type": "application/x-www-form-urlencoded; charset=utf-8"
            },
            data: $httpParamSerializer($scope.data)
        }
        $http(req).then(
            function(data){
                if (data.data.access_token) {
                    $rootScope.authenticated = true;
                    $window.sessionStorage.accessToken = data.data.access_token;
                } else {
                    $rootScope.authenticated = false;
                    delete $window.sessionStorage.accessToken;
                }
                callback && callback($rootScope.authenticated);
            }, function(){
                $rootScope.authenticated = false;
                delete $window.sessionStorage.accessToken;
                callback && callback(false);
            }
        );
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

}).controller('home', function($scope, $http, $window) {
    var headers = {
        "Accept" : "application/json", 
        authorization : "Bearer " + $window.sessionStorage.accessToken
    };
    
    $http.get("http://localhost:8082/um-webapp/api/roles/1", {
        headers : headers
    }).success(function(data) {
        $scope.role = data;
    })
});
