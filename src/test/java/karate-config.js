function() {    
  var env = karate.env; // get system property 'karate.env'
  karate.log('karate.env system property was:', env);
  if (!env) {
    env = 'dev';
  }
  var config = {
    env: env,
    service_url: 'http://localhost:8888'
  }
  if (env == 'dev') {
  } else if (env == 'jenkins') {
  }
  return config;
}