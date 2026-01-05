interface FormInputProps {
  title: string;
  placeholder: string;
  type?: string;
}

export function FormInput({ title, placeholder, type = "text" }: FormInputProps) {
  return (
    <div className="flex flex-col w-80 m-4">
      <label className="text-xl font-medium font-playfair text-primary dark:text-primary mb-1">
        {title}
      </label>
      
      <input
        required
        type={type}
        placeholder={placeholder}
        className="w-full font-sans font-normal leading-5 px-3 py-2 rounded-lg shadow-md border border-primary bg-white focus:border-primary focus:ring-2 focus:ring-primary outline-none transition-all"
      />
    </div>
  );
}
