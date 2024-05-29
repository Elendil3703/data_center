<template>
    <div class="login-container">
      <h2>登录</h2>
      <form @submit.prevent="login">
        <div>
          <label for="username" style="margin-right:10px;">Username</label>
          <input type="text" id="username" v-model="name" required />
        </div>
        <div>
          <label for="password" style="margin-right:10px;">Password</label>
          <input type="password" id="password" v-model="password" required />
        </div>
        <button type="submit">Login</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    name: 'LoginRoot',
    data() {
      return {
        name: '',
        password: ''
      };
    },
    methods: {
      async login() {
        try {
  const response = await this.$axios.post('/login', {
    username: this.name,
    password: this.password
  });
  const token = response.data.data.token;
  // Store the token in local storage
  localStorage.setItem('jwt', token);
  // Set the Authorization header for future requests
  this.$axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
  // Redirect to the DataQuery component
  this.$router.push({ name: 'DataQuery' });
} catch (error) {
  if (error.response && error.response.status === 400) {
    // 提示特定的错误信息
    console.error('Login failed:', error.response.data.msg);
    alert(`Login failed: ${error.response.data.msg}`);
  } else {
    // 处理其他错误
    console.error('Login failed:', error);
  }
}

      }
    }
  };
  </script>
  
  <style scoped>
  .login-container {
    max-width: 400px;
    margin: 50px auto;
    padding: 30px;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    background-color: #fff;
  }
  
  .login-container h2 {
    text-align: center;
    margin-bottom: 20px;
    color: #333;
  }
  
  .login-container form {
    display: flex;
    flex-direction: column;
  }
  
  .login-container form div {
    margin-bottom: 20px;
  }
  
  .login-container form label {
    margin-bottom: 8px;
    font-weight: bold;
    color: #555;
  }
  
  .login-container form input {
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
  }
  
  .login-container form input:focus {
    border-color: #007BFF;
    outline: none;
  }
  
  .login-container form button {
    padding: 10px;
    font-size: 16px;
    background-color: #007BFF;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  
  .login-container form button:hover {
    background-color: #0056b3;
  }
  </style>
  