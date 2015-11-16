angular.module('eva').factory('NetworkingService', ['$http', function($http) {
    var endpoint = 'http://localhost:8080';
    var factory = {};
    
    factory.get = function(path) {
        return $http.get(endpoint + path);
    };
    
    factory.post = function(path) {
        return $http.post(endpoint + path);
    }
    
    factory.put = function(path) {
        return $http.put(endpoint + path);
    }
    
    return factory;
}]);