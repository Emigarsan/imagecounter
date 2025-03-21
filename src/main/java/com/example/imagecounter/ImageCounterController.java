@PostMapping("/set")
@ResponseBody
public String setCounter(
        @RequestParam int image,
        @RequestParam int value,
        @RequestParam String key
) {
    final String ADMIN_KEY = "CLAVE123"; // clave definida

    if (!ADMIN_KEY.equals(key)) {
        return "ACCESO DENEGADO";
    }

    if (image == 1) {
        counter1 = value;
        return String.valueOf(counter1);
    } else if (image == 2) {
        counter2 = value;
        return String.valueOf(counter2);
    }

    return "0";
}
