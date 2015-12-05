angular.module('eva').factory('NetworkingService', ['$http', function($http) {
    var endpoint = 'http://localhost:8080';
    var factory = {};
    
    factory.get = function(path) {
        return $http.get(endpoint + path);
    };
    
    factory.post = function(path, data) {
        return $http.post(endpoint + path, data);
    }
    
    factory.put = function(path, data) {
        return $http.put(endpoint + path, data);
    }
    
    return factory;
}]);