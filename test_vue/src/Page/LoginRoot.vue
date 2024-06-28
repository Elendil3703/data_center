<template>
  <div class="login-container">
    <h2>登录</h2>
    <form @submit.prevent="login">
      <div class="input-group">
        <i class="fas fa-user"></i>
        <input type="text" id="username" v-model="name" required placeholder="Username" />
      </div>
      <div class="input-group">
        <i class="fas fa-lock"></i>
        <input type="password" id="password" v-model="password" required placeholder="Password" />
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
          name: this.name,
          password: this.password
        });
        const token = response.data.data.token;
        localStorage.setItem('jwt', token);
        this.$axios.defaults.headers.common['token'] = token;
        this.$router.push({ name: 'DataQuery' });
      } catch (error) {
        if (error.response && error.response.status === 400) {
          console.error('Login failed:', error.response.data.message);
          alert(`Login failed: ${error.response.data.message}`);
        } else {
          console.error('Login failed:', error);
        }
      }
    }
  }
};
</script>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css');

body {
  background: linear-gradient(to right, #ff7e5f, #feb47b);
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  margin: 0;
  font-family: 'Arial', sans-serif;
}

.login-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 30px;
  border: 1px solid #ddd;
  border-radius: 16px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  background-color: #fff;
  text-align: center;
}

.login-container h2 {
  margin-bottom: 20px;
  color: #333;
}

.login-container form {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.input-group {
  position: relative;
  margin-bottom: 20px;
  width: 100%;
}

.input-group i {
  position: absolute;
  left: 15px;
  top: 50%;
  transform: translateY(-50%);
  color: #aaa;
}

.input-group input {
  width: 100%;
  padding: 10px 10px 10px 40px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 25px;
  box-sizing: border-box;
}

.input-group input:focus {
  border-color: #007BFF;
  outline: none;
}

button {
  padding: 10px 20px;
  font-size: 16px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #0056b3;
}
</style>
