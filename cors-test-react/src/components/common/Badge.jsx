import React from 'react';
import { cn } from '../../utils/cn';

const badgeVariants = {
  variant: {
    default: 'bg-gray-100 text-gray-800',
    primary: 'bg-blue-100 text-blue-800',
    secondary: 'bg-gray-100 text-gray-800',
    success: 'bg-green-100 text-green-800',
    warning: 'bg-yellow-100 text-yellow-800',
    danger: 'bg-red-100 text-red-800',
    info: 'bg-blue-100 text-blue-800',
  },
  size: {
    sm: 'px-2 py-0.5 text-xs',
    md: 'px-2.5 py-1 text-sm',
    lg: 'px-3 py-1.5 text-base',
  },
};

const Badge = ({
  className,
  variant = 'default',
  size = 'md',
  children,
  ...props
}) => {
  return (
    <span
      className={cn(
        // Base styles
        'inline-flex items-center font-medium rounded-full',
        
        // Variants
        badgeVariants.variant[variant],
        badgeVariants.size[size],
        
        className
      )}
      {...props}
    >
      {children}
    </span>
  );
};

export default Badge;