<template>
    <v-form
      ref="form"
      align="center"
      v-model="valid"
      :lazy-validation="lazy"
    >
      <v-text-field
        v-model="name"
        label="Name"
        required
      ></v-text-field>

      <v-text-field
        label="E-mail"
        required
      ></v-text-field>

      <v-text-field
        label="Username"
        required
      ></v-text-field>

      <v-text-field
        label="Password"
        required
      ></v-text-field>

      <v-text-field
        label="Confirm Password"
        required
      ></v-text-field>

      <v-checkbox
        v-model="checkbox"
        :rules="[v => !!v || 'You must agree to continue!']"
        label="I agree to the Terms and Conditions"
        required
      ></v-checkbox>

      <v-btn
        color="success"
        
      >
        Register
      </v-btn>
    </v-form>

</template>

<script>
  export default {
    
  }
</script>