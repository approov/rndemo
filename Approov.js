import {NativeModules} from 'react-native';

const fetchWithToken = (input, options) => {
    return NativeModules.Approov.fetchApproovToken(input)
        .then(token => {
            let optionsA = (options? {...options, headers:{ ...options.headers}}:{headers: {}});
            optionsA.headers['Approov-Token'] = token;
            //alert('optionsA: ' + JSON.stringify(optionsA, null, 2), 'Response');

            return fetch(input, optionsA)
                .then((response) => {
                    if (response.ok) {
                        return response;
                    }
                    else {
                        throw new Error('HTTP response status is ' + response.status);
                    }
                })
                .catch((error) => {
                    throw error;
                })
        })
        .catch((error) => {
            throw error;
        })
};

const Approov = Object.assign({ fetch: fetchWithToken }, NativeModules.Approov);

export default Approov;

// end of file
