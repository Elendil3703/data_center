<template>
  <button class="btn" @click="handleClick">
    <slot></slot>
  </button>
</template>
<script>
export default {
  methods: {
    handleClick() {
      this.$emit('click');
    }
  }
}
</script>